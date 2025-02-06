using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace CLDV6211.POE.Models
{
    public class Car
    {
        [Required]
        [Key]
        public string CarNo { get; set; }
        public string CarModel { get; set; }
        public int KMTravelled { get; set; }
        public int ServiceKM { get; set; }
        public string Available { get; set; }

        [Display(Name = "Car Make")]
        public string CarMake { get; set; }
        [ForeignKey("CarMake")]
        public CarMake CarMakes { get; set; }

        [Display(Name = "Car Body Type")]
        public string TypeID { get; set; }
        [ForeignKey("CarBodyType")]
        public CarBodyType TypeIDs { get; set; }
    }
}



