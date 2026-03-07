// Load guests from database
document.addEventListener("DOMContentLoaded", loadGuests);

function loadGuests() {

  fetch("/OceanResort/getGuests")
      .then((res) => res.json())
      .then((data) => {

        const table = document.querySelector("#guestTable tbody");
        table.innerHTML = "";

        data.forEach((guest) => {

          const row = `
        <tr>
            <td>${guest.guest_id}</td>
            <td>${guest.name}</td>
            <td>${guest.phone}</td>
            <td>${guest.nic}</td>

            <td>
                <span class="reservation-badge none">
                    No Active Reservation
                </span>
            </td>

            <td>
                <button class="icon-btn edit">
                    <span class="material-symbols-outlined">edit</span>
                </button>

                <button class="icon-btn delete">
                    <span class="material-symbols-outlined">delete</span>
                </button>
            </td>
        </tr>
        `;

          table.innerHTML += row;

        });

      });

}

// Search guest
document.getElementById("searchInput").addEventListener("keyup", function () {

  const value = this.value.toLowerCase();
  const rows = document.querySelectorAll("#guestTable tbody tr");

  rows.forEach((row) => {
    row.style.display = row.textContent.toLowerCase().includes(value)
        ? ""
        : "none";
  });

});

// Navigation
function navigate(page) {
  window.location.href = page + ".html";
}

function logout() {
  window.location.href = "login.html";
}