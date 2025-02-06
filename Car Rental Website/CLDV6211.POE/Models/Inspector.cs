using System.ComponentModel.DataAnnotations;

namespace CLDV6211.POE.Models
{
    public class Inspector
    {
        [Required]
        [Key]
        public string InspectorNo { get; set; }
        public string InspectorName { get; set; }
        public string IEmail { get; set; }
        public string IMobile { get; set; }
    }
}
