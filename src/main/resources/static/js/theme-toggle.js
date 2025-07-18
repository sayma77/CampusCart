document.addEventListener("DOMContentLoaded", function () {
    const toggleBtn = document.getElementById("darkToggle");
    const themeIcon = document.getElementById("theme-icon");
    const htmlEl = document.documentElement;

    if (localStorage.theme === "dark") {
        htmlEl.classList.add("dark");
        themeIcon.textContent = "🌙";
    } else {
        htmlEl.classList.remove("dark");
        themeIcon.textContent = "🌞";
    }

    toggleBtn.addEventListener("click", () => {
        htmlEl.classList.toggle("dark");
        const isDark = htmlEl.classList.contains("dark");
        themeIcon.textContent = isDark ? "🌙" : "🌞";
        localStorage.theme = isDark ? "dark" : "light";
    });
});