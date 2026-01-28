import axios from 'axios';
import './style.css';

const API_URL = 'http://localhost:8080/api';

// Axios Instance
const api = axios.create({
    baseURL: API_URL,
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

// Auth Functions
export const login = async (email, password) => {
    try {
        const response = await api.post('/auth/login', { email, password });
        localStorage.setItem('token', response.data.token);
        localStorage.setItem('user', JSON.stringify(response.data.user || {})); // Assuming user info is returned
        window.location.href = '/';
    } catch (error) {
        console.error('Login failed', error);
        alert('Login failed: ' + (error.response?.data?.message || error.message));
    }
};

export const register = async (userData) => {
    try {
        const response = await api.post('/auth/register', userData);
        localStorage.setItem('token', response.data.token);
        window.location.href = '/';
    } catch (error) {
        console.error('Registration failed', error);
        alert('Registration failed: ' + (error.response?.data?.message || error.message));
    }
};

export const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = '/login';
};

// Validates current session
export const isAuthenticated = () => {
    return !!localStorage.getItem('token');
};

// Navbar Injection
export const setupNavbar = () => {
    const navbar = document.getElementById('navbar');
    if (!navbar) return;

    const isAuth = isAuthenticated();

    navbar.innerHTML = `
        <nav id="mainNav" class="fixed w-full z-50 transition-all duration-500 py-3 bg-surface/80 backdrop-blur-lg border-b border-dark-border">
            <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div class="flex justify-between h-14 items-center">
                    <div class="flex items-center space-x-8">
                        <a href="/" class="flex items-center space-x-2 group">
                            <span class="text-2xl font-bold text-white group-hover:text-primary transition-colors">
                                RealEstate
                            </span>
                        </a>
                        <div class="hidden md:flex items-center bg-dark-card rounded-full px-2 py-1 border border-dark-border">
                            <a href="/" class="text-white bg-dark-border px-4 py-1.5 rounded-full text-sm font-medium transition-all">Home</a>
                            <a href="/properties" class="text-gray-400 hover:text-white px-4 py-1.5 rounded-full text-sm font-medium transition-all">Properties</a>
                            <a href="#" class="text-gray-400 hover:text-white px-4 py-1.5 rounded-full text-sm font-medium transition-all">About</a>
                            <a href="#" class="text-gray-400 hover:text-white px-4 py-1.5 rounded-full text-sm font-medium transition-all">Contact</a>
                        </div>
                    </div>
                    <div class="flex items-center space-x-3">
                        ${isAuth ? `
                            <button id="logoutBtn" class="btn-secondary text-sm px-4 py-2">
                                Logout
                            </button>
                        ` : `
                            <a href="/login" class="btn-secondary text-sm px-5 py-2">Login</a>
                            <a href="/register" class="btn-primary text-sm px-5 py-2">
                                Sign up
                            </a>
                        `}
                    </div>
                </div>
            </div>
        </nav>
    `;

    // Liquid Glass Effect Logic
    const mainNav = document.getElementById('mainNav');
    if (mainNav) {
        window.addEventListener('scroll', () => {
            if (window.scrollY > 20) {
                mainNav.classList.add('navbar-scrolled');
                mainNav.classList.remove('bg-surface/80');
            } else {
                mainNav.classList.remove('navbar-scrolled');
                mainNav.classList.add('bg-surface/80');
            }
        });
    }

    if (isAuth) {
        document.getElementById('logoutBtn').addEventListener('click', logout);
    }
};

// API Exports for other files
export const propertyApi = {
    getAll: () => api.get('/properties'),
    getById: (id) => api.get(`/properties/${id}`),
    search: (query) => api.get(`/properties/search?q=${query}`),
    getByType: (type) => api.get(`/properties/type/${type}`)
};

// Footer Injection
export const setupFooter = () => {
    let footer = document.getElementById('footer');
    if (!footer) {
        // Create footer if it doesn't exist in HTML
        footer = document.createElement('footer');
        footer.id = 'footer';
        document.body.appendChild(footer);
    }

    footer.innerHTML = `
        <div class="bg-surface border-t border-dark-border text-white pt-16 pb-8 mt-auto">
            <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div class="grid grid-cols-1 md:grid-cols-4 gap-12 mb-12">
                    <div class="space-y-4">
                        <div class="text-2xl font-bold text-primary">
                            RealEstate
                        </div>
                        <p class="text-gray-400 text-sm leading-relaxed">
                            Transforming the way you find your dream home. Premium properties, trusted agents, and seamless experiences.
                        </p>
                    </div>
                    
                    <div>
                        <h3 class="text-lg font-semibold mb-4 text-white">Quick Links</h3>
                        <ul class="space-y-2 text-gray-400">
                            <li><a href="/" class="hover:text-primary transition-colors">Home</a></li>
                            <li><a href="/properties" class="hover:text-primary transition-colors">Properties</a></li>
                            <li><a href="/login" class="hover:text-primary transition-colors">Login</a></li>
                            <li><a href="/register" class="hover:text-primary transition-colors">Register</a></li>
                        </ul>
                    </div>

                    <div>
                        <h3 class="text-lg font-semibold mb-4 text-white">Contact</h3>
                        <ul class="space-y-2 text-gray-400 text-sm">
                            <li class="flex items-center"><span class="mr-2">📍</span> 123 Innovation Dr, Tech City</li>
                            <li class="flex items-center"><span class="mr-2">📧</span> support@realestate.com</li>
                            <li class="flex items-center"><span class="mr-2">📞</span> +1 (555) 123-4567</li>
                        </ul>
                    </div>

                    <div>
                        <h3 class="text-lg font-semibold mb-4 text-white">Follow Us</h3>
                        <div class="flex space-x-4">
                            <a href="#" class="w-10 h-10 rounded-full bg-dark-card border border-dark-border flex items-center justify-center hover:border-primary hover:text-primary transition-all duration-300">
                                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24"><path d="M24 4.557c-.883.392-1.832.656-2.828.775 1.017-.609 1.798-1.574 2.165-2.724-.951.564-2.005.974-3.127 1.195-.897-.957-2.178-1.555-3.594-1.555-3.179 0-5.515 2.966-4.797 6.045-4.091-.205-7.719-2.165-10.148-5.144-1.29 2.213-.669 5.108 1.523 6.574-.806-.026-1.566-.247-2.229-.616-.054 2.281 1.581 4.415 3.949 4.89-.693.188-1.452.232-2.224.084.626 1.956 2.444 3.379 4.6 3.419-2.07 1.623-4.678 2.348-7.29 2.04 2.179 1.397 4.768 2.212 7.548 2.212 9.142 0 14.307-7.721 13.995-14.646.962-.695 1.797-1.562 2.457-2.549z"/></svg>
                            </a>
                            <a href="#" class="w-10 h-10 rounded-full bg-dark-card border border-dark-border flex items-center justify-center hover:border-primary hover:text-primary transition-all duration-300">
                                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24"><path d="M9 8h-3v4h3v12h5v-12h3.642l.358-4h-4v-1.667c0-.955.192-1.333 1.115-1.333h2.885v-5h-3.808c-3.596 0-5.192 1.583-5.192 4.615v3.385z"/></svg>
                            </a>
                            <a href="#" class="w-10 h-10 rounded-full bg-dark-card border border-dark-border flex items-center justify-center hover:border-primary hover:text-primary transition-all duration-300">
                                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24"><path d="M12 2.163c3.204 0 3.584.012 4.85.07 3.252.148 4.771 1.691 4.919 4.919.058 1.265.069 1.645.069 4.849 0 3.205-.012 3.584-.069 4.849-.149 3.225-1.664 4.771-4.919 4.919-1.266.058-1.644.069-4.85.069-3.204 0-3.584-.012-4.849-.069-3.225-.149-4.771-1.664-4.919-4.919-.058-1.265-.069-1.644-.069-4.849 0-3.204.012-3.584.069-4.849.149-3.225 1.664-4.771 4.919-4.919 1.265-.057 1.645-.069 4.849-.069zm0-2.163c-3.259 0-3.667.014-4.947.072-4.358.2-6.78 2.618-6.98 6.98-.059 1.281-.073 1.689-.073 4.948 0 3.259.014 3.668.072 4.948.2 4.358 2.618 6.78 6.98 6.98 1.281.058 1.689.072 4.948.072 3.259 0 3.668-.014 4.948-.072 4.354-.2 6.782-2.618 6.979-6.98.059-1.28.073-1.689.073-4.948 0-3.259-.014-3.667-.072-4.947-.196-4.354-2.617-6.78-6.979-6.98-1.281-.059-1.69-.073-4.949-.073zm0 5.838c-3.403 0-6.162 2.759-6.162 6.162s2.759 6.163 6.162 6.163 6.162-2.759 6.162-6.163c0-3.403-2.759-6.162-6.162-6.162zm0 10.162c-2.209 0-4-1.79-4-4 0-2.209 1.791-4 4-4s4 1.791 4 4c0 2.21-1.791 4-4 4zm6.406-11.845c-.796 0-1.441.645-1.441 1.44s.645 1.44 1.441 1.44c.795 0 1.439-.645 1.439-1.44s-.644-1.44-1.439-1.44z"/></svg>
                            </a>
                        </div>
                    </div>
                </div>
                <div class="border-t border-dark-border pt-8 text-center text-gray-500 text-sm">
                    &copy; ${new Date().getFullYear()} Real Estate Inc. All rights reserved.
                </div>
            </div>
        </div>
    `;
};

document.addEventListener('DOMContentLoaded', () => {
    setupNavbar();
    setupFooter();
});
