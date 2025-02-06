using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace CLDV6211.POE.Models
{
    public class ReturnCar
    {
        [Required]
        [Key]
        public int ReturnCode { get; set; }
        public string CarNo { get; set; }
        public string InspectorNo { get; set; }
        public string DriverNo { get; set; }
        public DateTime ReturnDate { get; set; }
        public int ElapsedDate { get; set; }
        public decimal Fine { get; set; }

        [Display(Name = "Car Number")]
        public Car Car { get; set; }
        [ForeignKey("CarNo")]
        public Car Cars { get; set; }

        [Display(Name = "Inspector Number")]
        public Inspector Inspector { get; set; }
        [ForeignKey("InspectorNo")]
        public Inspector Inspectors { get; set; }

        [Display(Name = "Driver Number")]
        public Driver Driver { get; set; }
        [ForeignKey("DriverNo")]
        public Driver Drivers { get; set; }
    }
}
