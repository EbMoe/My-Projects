using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace CLDV6211.POE.Models
{
    public class CarBodyType
    {
        [Required]
        [Key]
        public string TypeID { get; set; }
        public string TypeDescription { get; set; }
    }
}
