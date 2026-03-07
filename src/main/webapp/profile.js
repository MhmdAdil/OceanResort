// Staff data
const staffData = {
  staffId: "S001",
  name: "John Doe",
  address: "123 Ocean Street, Colombo",
  email: "john.doe@oceanresort.com",
  phone: "0712345678",
  type: "Receptionist",
  age: 30,
  profilePic: "images/default-user.png",
};

// Elements
const modal = document.getElementById("profileModal");
const openBtn = document.getElementById("staffImage");
const closeBtn = document.getElementById("closeProfile");

// Open modal
openBtn.onclick = function () {
  modal.style.display = "block";
  loadStaffData();
};

// Close modal
closeBtn.onclick = function () {
  modal.style.display = "none";
};

// Close if clicked outside
window.onclick = function (e) {
  if (e.target == modal) {
    modal.style.display = "none";
  }
};

// Load staff data into the modal
function loadStaffData() {
  document.getElementById("staffHeading").textContent = staffData.name;
  document.getElementById("staffId").value = staffData.staffId;
  document.getElementById("staffName").value = staffData.name;
  document.getElementById("staffAddress").value = staffData.address;
  document.getElementById("staffEmail").value = staffData.email;
  document.getElementById("staffPhone").value = staffData.phone;
  document.getElementById("staffType").value = staffData.type;
  document.getElementById("staffAge").value = staffData.age;
  document.getElementById("profilePopupImage").src = staffData.profilePic;
}

// Change profile picture
document
  .getElementById("profileUpload")
  .addEventListener("change", function () {
    const file = this.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
        document.getElementById("profilePopupImage").src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  });
