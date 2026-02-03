import './style.css';

// Check for Admin Role immediately
const userStr = localStorage.getItem('user');
if (!userStr) {
    window.location.href = '/login.html';
} else {
    const user = JSON.parse(userStr);
    if (user.role !== 'ADMIN') {
        alert('Access Denied: Admins Only');
        window.location.href = '/user-dashboard.html';
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const user = JSON.parse(localStorage.getItem('user'));

    // Populate Admin Details
    const sidebarName = document.getElementById('sidebarAdminName');
    const sidebarEmail = document.getElementById('sidebarAdminEmail');
    const topbarName = document.getElementById('topbarAdminName');

    if (sidebarName) sidebarName.textContent = user.name || 'Admin';
    if (sidebarEmail) sidebarEmail.textContent = user.email || 'admin@example.com';
    if (topbarName) topbarName.textContent = user.name || 'Admin';

    // Logout Logic
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            window.location.href = '/login.html';
        });
    }

    // Example: You could fetch students here
    // loadStudents();
});
