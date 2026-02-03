# Admin Dashboard Documentation

## Overview

The Admin Dashboard is a comprehensive property management interface for administrators of the Real Estate platform. It provides tools to manage properties, agents, appointments, and inquiries.

## Features

### Authentication
- **Role-based Access Control**: Only users with `ADMIN` role can access this dashboard
- **Auto-redirect**: Non-admin users are redirected to the user dashboard
- **Session Management**: Logout functionality clears local storage and redirects to login

### Responsive Design
- **Desktop View (≥768px)**: Fixed sidebar with full navigation
- **Mobile View (<768px)**: Collapsible sidebar with hamburger menu
- **Smooth Transitions**: Sidebar slides in/out with animation (300ms ease-in-out)
- **Overlay**: Dark overlay on mobile when sidebar is open

## UI Structure

### Sidebar
Located on the left side, contains:
1. **Logo & Branding**: RealEstate Admin logo with "Management Panel" tagline
2. **Admin Profile**: Avatar, name, and email of logged-in admin
3. **Navigation Menu**:
   - Dashboard (overview)
   - Properties (active section)
   - Agents
   - Appointments
   - Inquiries
4. **Logout Button**: Bottom of sidebar

### Top Bar
Contains:
- Mobile menu toggle button (hamburger icon)
- Admin Dashboard title
- System status indicator (Online/Offline)
- Notification bell with unread indicator
- Admin profile quick view

### Main Content Area
- **Breadcrumb Navigation**: Shows current location (Dashboard > Properties)
- **Properties Management Section**:
  - Header with total count and action buttons
  - Filters: Search, Property Type, Status, City
  - Data Table: Title, Price, Type, Location, Agent, Status, Actions
  - Pagination controls

## Technology Stack

- **HTML5**: Semantic markup
- **Tailwind CSS**: Utility-first styling
- **JavaScript (ES6+)**: Module-based, handles sidebar toggle and authentication
- **Vite**: Build tool and dev server
- **Plus Jakarta Sans & Inter**: Typography fonts

## File Dependencies

```
admin-dashboard.html
├── /src/admin.js (authentication logic, user data population)
├── /src/style.css (Tailwind CSS base styles)
└── Google Fonts (Plus Jakarta Sans, Inter)
```

## JavaScript Functions

### Sidebar Toggle (Inline Script)
```javascript
toggleSidebar()  // Opens or closes sidebar based on current state
openSidebar()    // Opens sidebar, shows overlay, locks body scroll
closeSidebar()   // Closes sidebar, hides overlay, restores body scroll
```

### Authentication (admin.js)
- Checks for user in localStorage
- Validates ADMIN role
- Populates admin name/email in UI
- Handles logout

## Customization

### Adding New Navigation Items
Add a new `<a>` tag inside the `<nav>` element following this pattern:
```html
<a href="#" class="flex items-center px-4 py-3 text-gray-600 hover:bg-gray-50 hover:text-blue-600 rounded-lg transition-colors">
    <svg class="w-5 h-5 mr-3" ...></svg>
    Menu Item Name
</a>
```

### Changing Active State
Add these classes to mark a nav item as active:
```html
class="... bg-blue-50 text-blue-600 ..."
```

## Browser Support

- Chrome 80+
- Firefox 75+
- Safari 13+
- Edge 80+

## Related Files

- `tailwind.config.js` - Contains Tailwind CSS configuration
- `src/style.css` - Global styles and Tailwind directives
- `src/admin.js` - Admin authentication and UI logic
