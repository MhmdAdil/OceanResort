document.addEventListener("DOMContentLoaded",loadHistory);

function loadHistory(){

    fetch("/OceanResort/getCheckoutHistory")

        .then(res=>res.json())

        .then(data=>{

            const table=document.querySelector("#historyTable tbody");

            table.innerHTML="";

            data.forEach(r=>{

                const row=`
<tr>

<td>${r.reservation_id}</td>
<td>${r.guest_name}</td>
<td>${r.room_type}</td>
<td>${r.guest_count}</td>
<td>${r.check_in}</td>
<td>${r.check_out}</td>
<td>LKR ${r.total_amount}</td>
<td>${r.checkout_time}</td>

</tr>
`;

                table.innerHTML+=row;

            });

        });

}