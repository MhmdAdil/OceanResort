document.addEventListener("DOMContentLoaded", function () {

    loadReservationIds();
    loadRoomTypes();

    const reservationDropdown = document.getElementById("reservationId");

    reservationDropdown.addEventListener("change", function () {

        const id = this.value;

        if (!id) return;

        fetch("/OceanResort/getReservationDetails?id=" + id)

            .then(response => response.json())

            .then(data => {

                console.log(data);   // for debugging

                document.getElementById("guestName").value = data.name || "";
                document.getElementById("phone").value = data.phone || "";
                document.getElementById("email").value = data.email || "";
                document.getElementById("nic").value = data.nic || "";
                document.getElementById("address").value = data.address || "";

                document.getElementById("roomType").value = data.room_type || "";
                document.getElementById("guestCount").value = data.guest_count || "";
                document.getElementById("checkIn").value = data.check_in || "";
                document.getElementById("checkOut").value = data.check_out || "";
                document.getElementById("note").value = data.special_note || "";

            })

            .catch(error => {
                console.error("Reservation load error:", error);
            });

    });

});


/* Load Reservation IDs */

function loadReservationIds(){

    fetch("/OceanResort/getReservationIds")

        .then(res => res.json())

        .then(data => {

            const dropdown = document.getElementById("reservationId");

            dropdown.innerHTML = "<option value=''>Select Reservation</option>";

            data.forEach(r => {

                const option = document.createElement("option");

                option.value = r.reservation_id;
                option.textContent = r.reservation_id;

                dropdown.appendChild(option);

            });

        });

}


/* Load Room Types */

function loadRoomTypes(){

    fetch("/OceanResort/getRoomTypes")

        .then(res => res.json())

        .then(data => {

            const dropdown = document.getElementById("roomType");

            dropdown.innerHTML = "<option value=''>Select Room Type</option>";

            data.forEach(type => {

                const option = document.createElement("option");

                option.value = type.name;
                option.textContent = type.name;

                dropdown.appendChild(option);

            });

        });

}