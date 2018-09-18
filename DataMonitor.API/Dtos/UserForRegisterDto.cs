using System.ComponentModel.DataAnnotations;

namespace DataMonitor.API.Dtos
{
    public class UserForRegisterDto
    {
        [Required]
        public string Username { get; set; }

        [Required]
        [StringLength(8, MinimumLength = 4, ErrorMessage = "Password length doesn't match")]
        public string Password { get; set; }
    }
}