document.addEventListener("DOMContentLoaded", loadReservations);

function loadReservations() {

    fetch("/OceanResort/getReservations")
        .then(response => response.json())
        .then(data => {

            const tbody = document.getElementById("reservationBody");
            tbody.innerHTML = "";

            data.forEach(r => {

                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${r.id}</td>

                    <td>
                        <strong>${r.name}</strong><br>
                        <span class="sub-text">${r.phone}</span>
                    </td>

                    <td>${r.roomType}</td>
                    <td>${r.guestCount}</td>

                    <td>${formatDate(r.checkIn)}</td>
                    <td>${formatDate(r.checkOut)}</td>

                    <td><span class="status active">Active</span></td>

                    <td>
                        <button class="view-btn"
                        onclick="openViewModal(
                            '${r.id}',
                            '${r.name}',
                            '${r.phone}',
                            '${r.email}',
                            '${r.roomType}',
                            '${r.guestCount}',
                            '${r.checkIn}',
                            '${r.checkOut}',
                            '${r.note}'
                        )">View</button>

                        <button class="edit-btn"
                        onclick="editReservation(
                            '${r.id}',
                            '${r.name}',
                            '${r.phone}',
                            '${r.email}',
                            '${r.nic}',
                            '${r.address}',
                            '${r.roomType}',
                            '${r.guestCount}',
                            '${r.checkIn}',
                            '${r.checkOut}',
                            '${r.note}'
                        )">Edit</button>
                    </td>
                `;

                tbody.appendChild(row);

            });

        })
        .catch(error => {
            console.error("Error loading reservations:", error);
        });
}

function formatDate(date) {

    if (!date) return "";

    const d = new Date(date);

    const day = d.toISOString().split("T")[0];

    const time = d.toLocaleTimeString([], {
        hour: '2-digit',
        minute: '2-digit'
    });
    return `${day}<br><span class="sub-text">${time}</span>`;
}

function openBillingPage(){
    window.location.href = "billing.html";
}

function openCheckoutHistory(){
    window.location.href = "checkout-history.html";
}