// Simulated guest data (later from Java backend)
const guestData = {
  guestId: "G001",
  name: "Ali Ahmed",
  phone: "0771234567",
  email: "ali@gmail.com",
  nic: "200012345678",
  address: "Colombo, Sri Lanka",
};

// Load guest data into form
window.onload = function () {
  document.getElementById("guestId").value = guestData.guestId;
  document.getElementById("guestName").value = guestData.name;
  document.getElementById("phone").value = guestData.phone;
  document.getElementById("email").value = guestData.email;
  document.getElementById("nic").value = guestData.nic;
  document.getElementById("address").value = guestData.address;
};

// Save updated guest
document
  .getElementById("editGuestForm")
  .addEventListener("submit", function (e) {
    e.preventDefault();

    alert("Guest details updated successfully!");

    // Later: send updated data to Java backend
    navigate("guests");
  });

function navigate(page) {
  window.location.href = page + ".html";
}

function logout() {
  window.location.href = "login.html";
}
