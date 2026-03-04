function navigate(page) {
  alert("Navigate to: " + page);
  // Later you can redirect:
  // window.location.href = page + ".html";
}

function logout() {
  if (confirm("Are you sure you want to exit?")) {
    window.location.href = "login.html";
  }
}

const staffImageTop = document.getElementById("staffImage");
const profilePopupImage = document.getElementById("profilePopupImage");
const profileUpload = document.getElementById("profileUpload");
const profileModal = document.getElementById("profileModal");
const closeProfile = document.getElementById("closeProfile");
const openProfile = document.getElementById("openProfile");

/* ================================
   LOAD IMAGE ON PAGE LOAD
================================ */
window.addEventListener("DOMContentLoaded", () => {
  const savedImage = localStorage.getItem("staffProfileImage");
  if (savedImage) {
    staffImageTop.src = savedImage;
    profilePopupImage.src = savedImage;
  }
});

/* ================================
   OPEN / CLOSE MODAL
================================ */
openProfile.addEventListener("click", () => {
  profileModal.style.display = "flex";
});

closeProfile.addEventListener("click", () => {
  profileModal.style.display = "none";
});

/* ================================
   SAVE IMAGE PERMANENTLY
================================ */
profileUpload.addEventListener("change", function () {
  const file = this.files[0];
  if (!file) return;

  const reader = new FileReader();
  reader.onload = () => {
    const imageData = reader.result;

    // Update images
    staffImageTop.src = imageData;
    profilePopupImage.src = imageData;

    // SAVE to localStorage
    localStorage.setItem("staffProfileImage", imageData);
  };
  reader.readAsDataURL(file);
});
