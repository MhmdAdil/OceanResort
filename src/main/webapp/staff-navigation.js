function navigateStaff(page){

    const routes = {

        dashboard: "staff-dashboard.html",
        reservation: "reservation-details.html",
        guest: "guest-details.html",
        payment: "billing.html",
        staffHelp: "staff-help.html",

        res: "add-reservation.html"

    };

    if(routes[page]){
        window.location.href = routes[page];
    }

}