using System.ComponentModel.DataAnnotations;
namespace CLDV6211.POE.Models
{
    public class Driver
    {
        [Required]
        [Key]
        public string DriverNo { get; set; }
        public string DriverName { get; set; }
        public string DrAddress { get; set; }
        public string DrEmail { get; set; }
        public string DrMobile { get; set; }
    }
}
