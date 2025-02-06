using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace CLDV6211.POE.Models
{
    public class Rental
    {
        [Required]
        [Key]
        public int RentalCode { get; set; }
        public string CarNo { get; set; }
        public string InspectorNo { get; set; }
        public string DriverNo { get; set; }
        public decimal RentalFee { get; set; }
        public DateTime Start_Date { get; set; }
        public DateTime End_Date { get; set; }

        [Display(Name = "Car")]
        public Car Car { get; set; }
        [ForeignKey("CarNo")]
        public Car Cars { get; set; }

        [Display(Name = "Inspector")]
        public Inspector Inspector { get; set; }
        [ForeignKey("InspectorNo")]
        public Inspector Inspectors { get; set; }

        [Display(Name = "Driver")]
        public Driver Driver { get; set; }
        [ForeignKey("DriverNo")]
        public Driver Drivers { get; set; }
    }
}
