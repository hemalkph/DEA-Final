/** @type {import('tailwindcss').Config} */
export default {
    content: [
        "./index.html",
        "./login.html",
        "./register.html",
        "./properties.html",
        "./view-property.html",
        "./src/**/*.{js,ts,jsx,tsx}",
    ],
    theme: {
        extend: {
            colors: {
                primary: '#F5C518', // Yellow/Gold accent
                secondary: '#1A1A1A', // Dark background
                accent: '#F5C518', // Yellow/Gold
                surface: '#0D0D0D', // Near black
                'dark-card': '#1E1E1E', // Slightly lighter card bg
                'dark-border': '#2A2A2A', // Subtle borders
                glass: 'rgba(30, 30, 30, 0.8)',
            },
            fontFamily: {
                sans: ['"Plus Jakarta Sans"', 'Inter', 'sans-serif'],
                display: ['"Plus Jakarta Sans"', 'sans-serif'],
            },
            backgroundImage: {
                'hero-pattern': "url('https://images.unsplash.com/photo-1600596542815-27a90b5aff29?ixlib=rb-1.2.1&auto=format&fit=crop&w=2000&q=80')",
            }
        },
    },
    plugins: [],
}
