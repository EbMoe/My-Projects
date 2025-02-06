 using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace CLDV6211.POE.Migrations
{
    public partial class firstmig : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "AspNetRoles",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    Name = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: true),
                    NormalizedName = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: true),
                    ConcurrencyStamp = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_AspNetRoles", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "AspNetUsers",
                columns: table => new
                {
                    Id = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    FirstName = table.Column<string>(type: "nvarchar(255)", maxLength: 255, nullable: false),
                    LastName = table.Column<string>(type: "nvarchar(255)", maxLength: 255, nullable: false),
                    UserName = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: true),
                    NormalizedUserName = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: true),
                    Email = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: true),
                    NormalizedEmail = table.Column<string>(type: "nvarchar(256)", maxLength: 256, nullable: true),
                    EmailConfirmed = table.Column<bool>(type: "bit", nullable: false),
                    PasswordHash = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    SecurityStamp = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    ConcurrencyStamp = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    PhoneNumber = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    PhoneNumberConfirmed = table.Column<bool>(type: "bit", nullable: false),
                    TwoFactorEnabled = table.Column<bool>(type: "bit", nullable: false),
                    LockoutEnd = table.Column<DateTimeOffset>(type: "datetimeoffset", nullable: true),
                    LockoutEnabled = table.Column<bool>(type: "bit", nullable: false),
                    AccessFailedCount = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_AspNetUsers", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "CarBodyTypes",
                columns: table => new
                {
                    TypeID = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    TypeDescription = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CarBodyTypes", x => x.TypeID);
                });

            migrationBuilder.CreateTable(
                name: "CarMakes",
                columns: table => new
                {
                    CrMake = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    MakeDescription = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CarMakes", x => x.CrMake);
                });

            migrationBuilder.CreateTable(
                name: "Drivers",
                columns: table => new
                {
                    DriverNo = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    DriverName = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    DrAddress = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    DrEmail = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    DrMobile = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Drivers", x => x.DriverNo);
                });

            migrationBuilder.CreateTable(
                name: "Inspectors",
                columns: table => new
                {
                    InspectorNo = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    InspectorName = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    IEmail = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    IMobile = table.Column<string>(type: "nvarchar(max)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Inspectors", x => x.InspectorNo);
                });

            migrationBuilder.CreateTable(
                name: "AspNetRoleClaims",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    RoleId = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    ClaimType = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    ClaimValue = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_AspNetRoleClaims", x => x.Id);
                    table.ForeignKey(
                        name: "FK_AspNetRoleClaims_AspNetRoles_RoleId",
                        column: x => x.RoleId,
                        principalTable: "AspNetRoles",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "AspNetUserClaims",
                columns: table => new
                {
                    Id = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    UserId = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    ClaimType = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    ClaimValue = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_AspNetUserClaims", x => x.Id);
                    table.ForeignKey(
                        name: "FK_AspNetUserClaims_AspNetUsers_UserId",
                        column: x => x.UserId,
                        principalTable: "AspNetUsers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "AspNetUserLogins",
                columns: table => new
                {
                    LoginProvider = table.Column<string>(type: "nvarchar(128)", maxLength: 128, nullable: false),
                    ProviderKey = table.Column<string>(type: "nvarchar(128)", maxLength: 128, nullable: false),
                    ProviderDisplayName = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    UserId = table.Column<string>(type: "nvarchar(450)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_AspNetUserLogins", x => new { x.LoginProvider, x.ProviderKey });
                    table.ForeignKey(
                        name: "FK_AspNetUserLogins_AspNetUsers_UserId",
                        column: x => x.UserId,
                        principalTable: "AspNetUsers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "AspNetUserRoles",
                columns: table => new
                {
                    UserId = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    RoleId = table.Column<string>(type: "nvarchar(450)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_AspNetUserRoles", x => new { x.UserId, x.RoleId });
                    table.ForeignKey(
                        name: "FK_AspNetUserRoles_AspNetRoles_RoleId",
                        column: x => x.RoleId,
                        principalTable: "AspNetRoles",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_AspNetUserRoles_AspNetUsers_UserId",
                        column: x => x.UserId,
                        principalTable: "AspNetUsers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "AspNetUserTokens",
                columns: table => new
                {
                    UserId = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    LoginProvider = table.Column<string>(type: "nvarchar(128)", maxLength: 128, nullable: false),
                    Name = table.Column<string>(type: "nvarchar(128)", maxLength: 128, nullable: false),
                    Value = table.Column<string>(type: "nvarchar(max)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_AspNetUserTokens", x => new { x.UserId, x.LoginProvider, x.Name });
                    table.ForeignKey(
                        name: "FK_AspNetUserTokens_AspNetUsers_UserId",
                        column: x => x.UserId,
                        principalTable: "AspNetUsers",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Cars",
                columns: table => new
                {
                    CarNo = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    CarModel = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    KMTravelled = table.Column<int>(type: "int", nullable: false),
                    ServiceKM = table.Column<int>(type: "int", nullable: false),
                    Available = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    CarMake = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    TypeID = table.Column<string>(type: "nvarchar(max)", nullable: false),
                    CarBodyType = table.Column<string>(type: "nvarchar(450)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Cars", x => x.CarNo);
                    table.ForeignKey(
                        name: "FK_Cars_CarBodyTypes_CarBodyType",
                        column: x => x.CarBodyType,
                        principalTable: "CarBodyTypes",
                        principalColumn: "TypeID");
                    table.ForeignKey(
                        name: "FK_Cars_CarMakes_CarMake",
                        column: x => x.CarMake,
                        principalTable: "CarMakes",
                        principalColumn: "CrMake",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Rentals",
                columns: table => new
                {
                    RentalCode = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    CarNo = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    InspectorNo = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    DriverNo = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    RentalFee = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    Start_Date = table.Column<DateTime>(type: "datetime2", nullable: false),
                    End_Date = table.Column<DateTime>(type: "datetime2", nullable: false),
                    CarNo1 = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    InspectorNo1 = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    DriverNo1 = table.Column<string>(type: "nvarchar(450)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Rentals", x => x.RentalCode);
                    table.ForeignKey(
                        name: "FK_Rentals_Cars_CarNo",
                        column: x => x.CarNo,
                        principalTable: "Cars",
                        principalColumn: "CarNo",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Rentals_Cars_CarNo1",
                        column: x => x.CarNo1,
                        principalTable: "Cars",
                        principalColumn: "CarNo");
                    table.ForeignKey(
                        name: "FK_Rentals_Drivers_DriverNo",
                        column: x => x.DriverNo,
                        principalTable: "Drivers",
                        principalColumn: "DriverNo",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Rentals_Drivers_DriverNo1",
                        column: x => x.DriverNo1,
                        principalTable: "Drivers",
                        principalColumn: "DriverNo");
                    table.ForeignKey(
                        name: "FK_Rentals_Inspectors_InspectorNo",
                        column: x => x.InspectorNo,
                        principalTable: "Inspectors",
                        principalColumn: "InspectorNo",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Rentals_Inspectors_InspectorNo1",
                        column: x => x.InspectorNo1,
                        principalTable: "Inspectors",
                        principalColumn: "InspectorNo");
                });

            migrationBuilder.CreateTable(
                name: "Returns",
                columns: table => new
                {
                    ReturnCode = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    CarNo = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    InspectorNo = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    DriverNo = table.Column<string>(type: "nvarchar(450)", nullable: false),
                    ReturnDate = table.Column<DateTime>(type: "datetime2", nullable: false),
                    ElapsedDate = table.Column<int>(type: "int", nullable: false),
                    Fine = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    CarNo1 = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    InspectorNo1 = table.Column<string>(type: "nvarchar(450)", nullable: true),
                    DriverNo1 = table.Column<string>(type: "nvarchar(450)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Returns", x => x.ReturnCode);
                    table.ForeignKey(
                        name: "FK_Returns_Cars_CarNo",
                        column: x => x.CarNo,
                        principalTable: "Cars",
                        principalColumn: "CarNo",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Returns_Cars_CarNo1",
                        column: x => x.CarNo1,
                        principalTable: "Cars",
                        principalColumn: "CarNo");
                    table.ForeignKey(
                        name: "FK_Returns_Drivers_DriverNo",
                        column: x => x.DriverNo,
                        principalTable: "Drivers",
                        principalColumn: "DriverNo",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Returns_Drivers_DriverNo1",
                        column: x => x.DriverNo1,
                        principalTable: "Drivers",
                        principalColumn: "DriverNo");
                    table.ForeignKey(
                        name: "FK_Returns_Inspectors_InspectorNo",
                        column: x => x.InspectorNo,
                        principalTable: "Inspectors",
                        principalColumn: "InspectorNo",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Returns_Inspectors_InspectorNo1",
                        column: x => x.InspectorNo1,
                        principalTable: "Inspectors",
                        principalColumn: "InspectorNo");
                });

            migrationBuilder.CreateIndex(
                name: "IX_AspNetRoleClaims_RoleId",
                table: "AspNetRoleClaims",
                column: "RoleId");

            migrationBuilder.CreateIndex(
                name: "RoleNameIndex",
                table: "AspNetRoles",
                column: "NormalizedName",
                unique: true,
                filter: "[NormalizedName] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_AspNetUserClaims_UserId",
                table: "AspNetUserClaims",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_AspNetUserLogins_UserId",
                table: "AspNetUserLogins",
                column: "UserId");

            migrationBuilder.CreateIndex(
                name: "IX_AspNetUserRoles_RoleId",
                table: "AspNetUserRoles",
                column: "RoleId");

            migrationBuilder.CreateIndex(
                name: "EmailIndex",
                table: "AspNetUsers",
                column: "NormalizedEmail");

            migrationBuilder.CreateIndex(
                name: "UserNameIndex",
                table: "AspNetUsers",
                column: "NormalizedUserName",
                unique: true,
                filter: "[NormalizedUserName] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_Cars_CarBodyType",
                table: "Cars",
                column: "CarBodyType");

            migrationBuilder.CreateIndex(
                name: "IX_Cars_CarMake",
                table: "Cars",
                column: "CarMake");

            migrationBuilder.CreateIndex(
                name: "IX_Rentals_CarNo",
                table: "Rentals",
                column: "CarNo");

            migrationBuilder.CreateIndex(
                name: "IX_Rentals_CarNo1",
                table: "Rentals",
                column: "CarNo1");

            migrationBuilder.CreateIndex(
                name: "IX_Rentals_DriverNo",
                table: "Rentals",
                column: "DriverNo");

            migrationBuilder.CreateIndex(
                name: "IX_Rentals_DriverNo1",
                table: "Rentals",
                column: "DriverNo1");

            migrationBuilder.CreateIndex(
                name: "IX_Rentals_InspectorNo",
                table: "Rentals",
                column: "InspectorNo");

            migrationBuilder.CreateIndex(
                name: "IX_Rentals_InspectorNo1",
                table: "Rentals",
                column: "InspectorNo1");

            migrationBuilder.CreateIndex(
                name: "IX_Returns_CarNo",
                table: "Returns",
                column: "CarNo");

            migrationBuilder.CreateIndex(
                name: "IX_Returns_CarNo1",
                table: "Returns",
                column: "CarNo1");

            migrationBuilder.CreateIndex(
                name: "IX_Returns_DriverNo",
                table: "Returns",
                column: "DriverNo");

            migrationBuilder.CreateIndex(
                name: "IX_Returns_DriverNo1",
                table: "Returns",
                column: "DriverNo1");

            migrationBuilder.CreateIndex(
                name: "IX_Returns_InspectorNo",
                table: "Returns",
                column: "InspectorNo");

            migrationBuilder.CreateIndex(
                name: "IX_Returns_InspectorNo1",
                table: "Returns",
                column: "InspectorNo1");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "AspNetRoleClaims");

            migrationBuilder.DropTable(
                name: "AspNetUserClaims");

            migrationBuilder.DropTable(
                name: "AspNetUserLogins");

            migrationBuilder.DropTable(
                name: "AspNetUserRoles");

            migrationBuilder.DropTable(
                name: "AspNetUserTokens");

            migrationBuilder.DropTable(
                name: "Rentals");

            migrationBuilder.DropTable(
                name: "Returns");

            migrationBuilder.DropTable(
                name: "AspNetRoles");

            migrationBuilder.DropTable(
                name: "AspNetUsers");

            migrationBuilder.DropTable(
                name: "Cars");

            migrationBuilder.DropTable(
                name: "Drivers");

            migrationBuilder.DropTable(
                name: "Inspectors");

            migrationBuilder.DropTable(
                name: "CarBodyTypes");

            migrationBuilder.DropTable(
                name: "CarMakes");
        }
    }
}
