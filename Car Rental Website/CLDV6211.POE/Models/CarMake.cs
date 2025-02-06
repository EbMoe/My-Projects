using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace CLDV6211.POE.Models
{
    public class CarMake
    {
        [Required]
        [Key]
        public string CrMake { get; set; }
        public string MakeDescription { get; set; }

    }
}
