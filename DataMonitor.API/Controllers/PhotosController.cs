using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using AutoMapper;
using CloudinaryDotNet;
using CloudinaryDotNet.Actions;
using DataMonitor.API.Data;
using DataMonitor.API.Dtos;
using DataMonitor.API.Helpers;
using DataMonitor.API.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;

namespace DataMonitor.API.Controllers
{
    [Authorize]
    [Route("api/users/{userId}/photos")]
    [ApiController]
    public class PhotosController : ControllerBase
    {
        private readonly IDatingRepository reposiroty;
        private readonly IMapper mapper;
        private readonly IOptions<CloudinarySettings> cloudinaryConfig;
        private Cloudinary cloudinary;
        public PhotosController(IDatingRepository reposiroty, IMapper mapper,
        IOptions<CloudinarySettings> cloudinaryConfig)
        {
            this.cloudinaryConfig = cloudinaryConfig;
            this.mapper = mapper;
            this.reposiroty = reposiroty;

            Account account = new Account(
                cloudinaryConfig.Value.CloudName,
                cloudinaryConfig.Value.ApiKey,
                cloudinaryConfig.Value.ApiSecret
            );

            cloudinary = new Cloudinary(account);
        }

        [HttpGet("{id}", Name = "GetPhoto")]
        public async Task<IActionResult> GetPhoto(int id)
        {
            var photoFromRepo = await reposiroty.GetPhoto(id);

            var photo = mapper.Map<PhotoForReturnDto>(photoFromRepo);

            return Ok(photo);
        }

        [HttpPost]
        public async Task<IActionResult> AddPhototoUser(int userId,
             [FromForm]PhotoForCreationDto photoForCreationDto)
        {
            if (userId != int.Parse(User.FindFirst(ClaimTypes.NameIdentifier).Value))
                return Unauthorized();

            var userFromRepo = await reposiroty.GetUser(userId);

            var file = photoForCreationDto.File;
            
            var uploadResult = new ImageUploadResult();

            if (file.Length > 0)
            {
                using (var stream = file.OpenReadStream())
                {
                    var uploadParms  = new ImageUploadParams()
                    {
                        File = new FileDescription(file.Name, stream),
                        Transformation = new Transformation().Width(500)
                            .Height(500).Crop("fill").Gravity("face")
                    };

                    uploadResult = cloudinary.Upload(uploadParms);
                }
            }

            photoForCreationDto.Url = uploadResult.Uri.ToString();
            photoForCreationDto.PublicId = uploadResult.PublicId;

            var photo = mapper.Map<Photo>(photoForCreationDto);

            if (!userFromRepo.Photos.Any(u => u.IsMain))
                photo.IsMain = true;

            userFromRepo.Photos.Add(photo);

            if (await reposiroty.saveAll())
            {
                var photoToReturn = mapper.Map<PhotoForReturnDto>(photo);

                return CreatedAtRoute("GetPhoto", new { id = photo.Id}, photoToReturn);
            }

            return BadRequest("Could not add the photo");
        }

        [HttpPost("{id}/setMain")]
        public async Task<IActionResult> SetMainPhoto(int userId, int id)
        {
            if (userId != int.Parse(User.FindFirst(ClaimTypes.NameIdentifier).Value))
                return Unauthorized();

            var userFromRepo = await reposiroty.GetUser(userId);
            
            if (!userFromRepo.Photos.Any(p => p.Id == id))
                return Unauthorized();

            var photoFromRepo = await reposiroty.GetPhoto(id);

            if (photoFromRepo.IsMain) 
                return BadRequest("This is already main photo");
            
            var currentMainPhoto = await reposiroty.GetMainPhotoForUser(userId);
            
            currentMainPhoto.IsMain = false;

            photoFromRepo.IsMain = true;

            if (await reposiroty.saveAll())
                return NoContent();
            
            return BadRequest("Could not set photo to main");
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeletePhoto(int userId, int id) 
        {
            if (userId != int.Parse(User.FindFirst(ClaimTypes.NameIdentifier).Value))
                return Unauthorized();

            var userFromRepo = await reposiroty.GetUser(userId);

            if (!userFromRepo.Photos.Any(p => p.Id == id))
                return Unauthorized();

            var photoFromRepo = await reposiroty.GetPhoto(id);

            if (photoFromRepo.IsMain) 
                return BadRequest("You cannot delete your main photo");

            
            if (photoFromRepo.PublicId != null) 
            {
                var deleteParams = new DeletionParams(photoFromRepo.PublicId);

                var result = cloudinary.Destroy(deleteParams);

                if (result.Result == "ok") 
                {
                    reposiroty.Delete(photoFromRepo);
                }
            } 
            else 
            {
                reposiroty.Delete(photoFromRepo);
            }

            if (await reposiroty.saveAll())
                return Ok();
            
            return BadRequest("Failed to delete the photo");
        }
    }
}