document.addEventListener("DOMContentLoaded", function () {
    const toggleBtn = document.getElementById("darkToggle");
    const themeIcon = document.getElementById("theme-icon");
    const htmlEl = document.documentElement;

    // initial load (check localStorage or system preference)
    if (localStorage.theme === "dark" ||
       (!("theme" in localStorage) && window.matchMedia("(prefers-color-scheme: dark)").matches)) {
        htmlEl.classList.add("dark");
        themeIcon.textContent = "🌙";
    } else {
        htmlEl.classList.remove("dark");
        themeIcon.textContent = "🌞";
    }

    // toggle on click
    toggleBtn.addEventListener("click", () => {
        htmlEl.classList.toggle("dark");
        const isDark = htmlEl.classList.contains("dark");
        themeIcon.textContent = isDark ? "🌙" : "🌞";
        localStorage.theme = isDark ? "dark" : "light";
    });
});
