document.addEventListener("DOMContentLoaded",function(){

    loadReservationIds();

    document.getElementById("reservationId").addEventListener("change",loadBillDetails);

});

function loadReservationIds(){

    fetch("/OceanResort/getReservationIds")

        .then(res=>res.json())

        .then(data=>{

            const dropdown=document.getElementById("reservationId");

            dropdown.innerHTML="<option>Select Reservation</option>";

            data.forEach(r=>{

                const option=document.createElement("option");

                option.value=r.reservation_id;
                option.textContent=r.reservation_id;

                dropdown.appendChild(option);

            });

        });

}


function loadBillDetails(){

    const id=document.getElementById("reservationId").value;

    fetch("/OceanResort/getBillingDetails?id="+id)

        .then(res=>res.json())

        .then(data=>{

            document.getElementById("guestName").value=data.name;
            document.getElementById("roomType").value=data.room_type;
            document.getElementById("price").value=data.price;
            document.getElementById("checkIn").value=data.check_in;
            document.getElementById("checkOut").value=data.check_out;
            document.getElementById("nights").value=data.nights;
            document.getElementById("total").value=data.total;

        });

}

function generateBill(){

    const reservationId=document.getElementById("reservationId").value;

    fetch("/OceanResort/checkoutReservation",{

        method:"POST",

        headers:{
            "Content-Type":"application/x-www-form-urlencoded"
        },

        body:"reservationId="+reservationId

    })

        .then(res=>res.text())

        .then(msg=>{

            alert(msg);

            location.reload();

        });

}


function printBill(){

    const guest=document.getElementById("guestName").value;
    const room=document.getElementById("roomType").value;
    const price=parseFloat(document.getElementById("price").value);
    const nights=parseInt(document.getElementById("nights").value);
    const total=parseFloat(document.getElementById("total").value);

    document.getElementById("invDate").textContent=new Date().toLocaleDateString();
    document.getElementById("invNumber").textContent="INV-"+Date.now();
    document.getElementById("invGuest").textContent=guest;

    document.getElementById("invRoom").textContent=room+" Room";
    document.getElementById("invNights").textContent=nights;
    document.getElementById("invPrice").textContent="LKR "+price;
    document.getElementById("invAmount").textContent="LKR "+total;

    document.getElementById("invSubtotal").textContent="LKR "+total;
    document.getElementById("invTotal").textContent="LKR "+total;

    document.getElementById("invoicePopup").style.display="flex";

}


function closeInvoice(){

    document.getElementById("invoicePopup").style.display="none";

}