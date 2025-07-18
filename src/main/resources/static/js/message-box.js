function showMessage(text, type) {
  const msg = document.getElementById("message");
  msg.textContent = text;

  // Remove all previous color classes
  msg.classList.remove(
    "text-green-600",
    "bg-green-100",
    "text-red-600",
    "bg-red-100",
    "hidden"
  );

  // Add classes based on type
  if (type === "success") {
    msg.classList.add("text-green-600", "bg-green-100");
  } else if (type === "error") {
    msg.classList.add("text-red-600", "bg-red-100");
  }

  // Make message visible (if hidden)
  msg.style.display = "block";

  // Clear message after 4 seconds
  setTimeout(() => {
    msg.textContent = "";
    msg.style.display = "none";
    msg.classList.remove(
      "text-green-600",
      "bg-green-100",
      "text-red-600",
      "bg-red-100"
    );
    msg.classList.add("hidden");
  }, 4000);
}
