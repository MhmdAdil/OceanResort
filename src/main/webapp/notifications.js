const notifications = [
  {
    type: "info",
    icon: "event",
    title: "Upcoming Check-in",
    message: "Guest John Silva will check in to Room 204 at 2:00 PM today.",
    time: "10 minutes ago",
  },
  {
    type: "warning",
    icon: "schedule",
    title: "Late Check-out",
    message: "Room 305 has not been checked out yet. Please follow up.",
    time: "30 minutes ago",
  },
  {
    type: "success",
    icon: "check_circle",
    title: "Reservation Confirmed",
    message: "Reservation #RES1024 has been successfully confirmed.",
    time: "1 hour ago",
  },
  {
    type: "danger",
    icon: "error",
    title: "Payment Pending",
    message: "Payment pending for reservation #RES1012.",
    time: "2 hours ago",
  },
];

const list = document.getElementById("notificationList");

notifications.forEach((n) => {
  const card = document.createElement("div");
  card.className = `notification-card ${n.type}`;

  card.innerHTML = `
        <span class="material-symbols-outlined notification-icon">
            ${n.icon}
        </span>
        <div class="notification-content">
            <h4>${n.title}</h4>
            <p>${n.message}</p>
            <div class="notification-time">${n.time}</div>
        </div>
    `;

  list.appendChild(card);
});

/* Navigation placeholders */
function navigate(page) {
  alert("Navigate to " + page);
}

function logout() {
  alert("Logged out");
}
