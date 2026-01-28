# Real Estate Frontend - Vanilla JS & Tailwind CSS

This project is a modern, responsive real estate application frontend built with:
- **HTML5**
- **Vanilla JavaScript** (Modules)
- **Tailwind CSS**
- **Vite** (Build tool & Dev Server)
- **Axios** (API integration)

## 🚀 Getting Started

### Prerequisites
- Node.js (v16 or higher)
- NPM

### Installation

1.  Navigate to the project directory:
    ```bash
    cd frontend
    ```

2.  Install dependencies:
    ```bash
    npm install
    ```

### Running the Project

Start the development server:
```bash
npm run dev
```
The application will be available at `http://localhost:5173`.

### Building for Production

To create a production-ready build:
```bash
npm run build
```
The output will be in the `dist` directory.

## 📁 Project Structure

```
frontend/
├── src/
│   ├── main.js        # Core logic (Auth, Navbar, Global API)
│   └── style.css      # Tailwind directives & custom styles
├── index.html         # Home Page
├── login.html         # Login Page
├── register.html      # Registration Page
├── properties.html    # Property Listing Page
├── view-property.html # Property Details Page
├── tailwind.config.js # Tailwind CSS Configuration
└── vite.config.js     # Vite Configuration
```

## 🗄️ Database Requirements (V2 Updates)

To support the Categorization feature (Villas, Apartments, etc.), the backend `Property` entity and database table require an update:

**Property Table schema change:**
- **Add Column**: `category` (VARCHAR/ENUM)
- **Values**: `VILLA`, `APARTMENT`, `HOUSE`, `COMMERCIAL`

*Note: The frontend currently simulates this filtering using title/description matching until the backend is updated.*

## 🔌 API Integration

The frontend connects to a Spring Boot backend running on `http://localhost:8080`.
Ensure your backend is running before testing authentication or property data.
