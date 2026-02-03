# Property Listing Approval System - Specification

## Overview

This document specifies the Property Listing Approval System for the Real Estate application. The system allows sellers to submit property listings with images, which are stored with a PENDING status until an administrator reviews and approves them.

---

## User Stories

### Seller Flow
1. **As a Seller**, I want to submit a property listing with photos so that my property can be listed for sale.
2. **As a Seller**, I want my listing to be reviewed before going live to ensure quality.

### Admin Flow  
1. **As an Admin**, I want to view all pending listings so I can review submitted properties.
2. **As an Admin**, I want to approve or reject listings to maintain platform quality.
3. **As an Admin**, I want to see property images uploaded by sellers to verify authenticity.

---

## Data Model

### PropertyListing Entity (Enhanced Property Model)
The existing `Property` entity will be used with `PropertyStatus.PENDING` for new submissions.

| Field | Type | Description |
|-------|------|-------------|
| id | Long | Primary key |
| title | String | Property title |
| description | String | Property description |
| address | String | Property address |
| price | BigDecimal | Listing price |
| type | PropertyType | SALE or RENT |
| status | PropertyStatus | PENDING, AVAILABLE, SOLD, RENTED |
| imageUrl | String | Main thumbnail image path |
| imageUrls | List<String> | Additional image paths |
| bedrooms | Integer | Number of bedrooms |
| bathrooms | Integer | Number of bathrooms |
| areaSqFt | Double | Area in square feet |
| ownerName | String | **NEW** - Seller name |
| ownerPhone | String | **NEW** - Seller phone |
| ownerEmail | String | **NEW** - Seller email |
| createdAt | LocalDateTime | Submission timestamp |

### PropertyStatus Enum (Already Exists)
```java
public enum PropertyStatus {
    AVAILABLE,  // Approved and visible
    PENDING,    // Awaiting admin review
    SOLD,       // Transaction complete
    RENTED      // Currently rented
}
```

---

## API Endpoints

### Seller Endpoints

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/api/listings` | Submit new property listing with images | Optional |
| POST | `/api/files/upload-multiple` | Upload property images (existing) | None |

### Admin Endpoints

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/api/admin/pending` | Get all PENDING listings | Admin |
| PUT | `/api/admin/listings/{id}/approve` | Approve a listing | Admin |
| PUT | `/api/admin/listings/{id}/reject` | Reject a listing | Admin |

---

## File Upload Configuration

### Storage Path
- **Location**: `uploads/` directory (configurable via `file.upload-dir`)
- **URL Pattern**: `/api/files/{filename}` serves uploaded images
- **Static Resource**: WebMvcConfigurer maps `/uploads/**` for direct access

### Limits
- Max file size: 10MB per file
- Max request size: 50MB (for multiple uploads)
- Allowed types: image/* (JPEG, PNG, WebP, etc.)

---

## Admin Dashboard Integration

### New Sidebar Item
Add "Pending Listings" navigation item after "Agents" in admin sidebar with:
- Orange/yellow indicator badge showing count of pending items
- Click action loads `pendingListingsView`

### Pending Listings View
- Table displaying: Property Title, Price, Location, Seller Info, Submitted Date, Actions
- Image thumbnail preview
- Approve/Reject action buttons
- Modal for viewing full property details and all images

---

## Database Updates

No schema.sql changes required - JPA/Hibernate with `ddl-auto=update` will:
1. Add `owner_name`, `owner_phone`, `owner_email` columns to `properties` table
2. Existing `status` column already supports PENDING enum value

---

## Security Considerations

1. File upload validation (check mime types, sizes)
2. Admin endpoints require authentication (already configured)
3. Sanitize user input before database storage
4. Rate limiting on submission endpoint (future enhancement)
