using System;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using System.Threading.Tasks;
using DataMonitor.API.Data;
using DataMonitor.API.Dtos;
using DataMonitor.API.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;

namespace DataMonitor.API.Controllers
{

    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly IAuthRepository repository;
        private readonly IConfiguration configuration;
        public AuthController(IAuthRepository repository, IConfiguration configuration)
        {
            this.repository = repository;
            this.configuration = configuration;
        }

        [HttpPost("register")]
        public async Task<IActionResult> Register(UserForRegisterDto userForRegisterDto)
        {
            userForRegisterDto.Username = userForRegisterDto.Username.ToLower();
            if (await repository.UserExist(userForRegisterDto.Username))
                return BadRequest("Username already exist");

            var userToCreate = new User
            {
                Username = userForRegisterDto.Username
            };

            var createdUser = await repository.Register(userToCreate, userForRegisterDto.Password);

            // return Ok(new {
            //     message = "Created User"
            // });
            return StatusCode(201);

        }

        [HttpPost("login")]
        public async Task<IActionResult> Login(UserForLoginDto userForLoginDto)
        {
            var userFromRepo = await repository.Login(userForLoginDto.Username.ToLower(), userForLoginDto.Password);

            if (userFromRepo == null)
                return Unauthorized();

            var claims = new[]
            {
                new Claim(ClaimTypes.NameIdentifier, userFromRepo.Id.ToString()),
                new Claim(ClaimTypes.Name, userForLoginDto.Username)
            };

            // var key = new SymmetricSecurityKey(Encoding.UTF8
            //     .GetBytes(configuration.GetSection("Appsettings.Token").Value));
            var key = new SymmetricSecurityKey(Encoding.UTF8
                .GetBytes("super secret key"));
            var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha512Signature);

            var tokenDescriptor = new SecurityTokenDescriptor
            {
                Subject = new ClaimsIdentity(claims),
                Expires = DateTime.Now.AddDays(1),
                SigningCredentials = creds
            };

            var tockenHandler = new JwtSecurityTokenHandler();

            var token = tockenHandler.CreateToken(tokenDescriptor);

            return Ok(new {
                token = tockenHandler.WriteToken(token)
            });
        }
    }
}