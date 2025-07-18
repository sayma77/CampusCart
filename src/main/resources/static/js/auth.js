function logout() {
  localStorage.removeItem("token");

  fetch("http://localhost:8080/api/auth/logout", {
    method: "POST",
    credentials: "include",
  });

  window.location.href = "/login.html";
}

function parseJwt(token) {
  try {
    return JSON.parse(atob(token.split(".")[1]));
  } catch (e) {
    return null;
  }
}

function isAuthenticated() {
  const token = localStorage.getItem("token");
  if (!token) return false;
  const payload = parseJwt(token);
  const now = Math.floor(Date.now() / 1000);
  if (!payload || payload.exp < now) {
    localStorage.removeItem("token");
    return false;
  }
  return true;
}

function validate_auth() {
  const loggedin = isAuthenticated();
  if (!loggedin) {
    window.location.href = "/signin.html";
  }
  return loggedin;
}

function logout() {
  localStorage.removeItem("token");
  window.location.href = "/signin.html";
}
