# Property Listing Approval System - Implementation Plan

## Overview

This plan outlines the implementation steps for the Property Listing Approval System, enabling sellers to submit property listings that require admin approval before publication.

---

## Phase 1: Backend - Model Updates

### 1.1 Update Property Entity
**File**: `src/main/java/com/example/final_project/model/Property.java`

Add seller contact fields:
```java
private String ownerName;
private String ownerPhone;
private String ownerEmail;
```

### 1.2 Configure Static Resource Serving
**File**: `src/main/java/com/example/final_project/config/WebConfig.java` [NEW]

Create WebMvcConfigurer to serve uploaded images as static resources:
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
```

---

## Phase 2: Backend - API Endpoints

### 2.1 Create ListingController
**File**: `src/main/java/com/example/final_project/controller/ListingController.java` [NEW]

```
POST /api/listings - Submit property listing (sets status=PENDING)
```

### 2.2 Create AdminListingController  
**File**: `src/main/java/com/example/final_project/controller/AdminListingController.java` [NEW]

```
GET  /api/admin/pending          - Get all PENDING properties
PUT  /api/admin/listings/{id}/approve - Set status=AVAILABLE
PUT  /api/admin/listings/{id}/reject  - Delete or mark rejected
```

### 2.3 Update PropertyService
**File**: `src/main/java/com/example/final_project/service/PropertyService.java`

Add methods:
- `List<Property> getPendingProperties()`
- `Property approveProperty(Long id)`
- `void rejectProperty(Long id)`

### 2.4 Update PropertyRepository
**File**: `src/main/java/com/example/final_project/repository/PropertyRepository.java`

Add query:
```java
List<Property> findByStatus(PropertyStatus status);
```

---

## Phase 3: Frontend - Sell Property Form Update

### 3.1 Update sell-property.html
**File**: `frontend/sell-property.html`

Modify form submission to:
1. Upload images via `/api/files/upload-multiple`
2. Submit listing via `/api/listings` with image paths
3. Show success message indicating "pending review"

---

## Phase 4: Admin Dashboard - Pending Listings

### 4.1 Add Sidebar Navigation
**File**: `frontend/admin-dashboard.html`

Add "Pending Listings" nav item after "Agents" with badge count.

### 4.2 Create Pending Listings View
**File**: `frontend/admin-dashboard.html`

Add `pendingListingsView` section with:
- Table: Title, Price, Location, Seller, Date, Actions
- Image preview column
- Approve/Reject buttons
- View details modal

### 4.3 Add JavaScript Functions
**File**: `frontend/admin-dashboard.html`

Add:
- `loadPendingListings()` - Fetch from `/api/admin/pending`
- `showPendingListingsView()` - Toggle view visibility
- `approveListing(id)` - API call + refresh
- `rejectListing(id)` - API call + refresh

---

## Phase 5: Testing & Verification

### 5.1 Manual Test Cases
1. Submit listing via sell-property page → verify PENDING in database
2. View pending listings in admin dashboard → verify all fields display
3. Approve listing → verify status changes to AVAILABLE
4. Reject listing → verify removal/status change
5. Verify images display correctly via static resource URL

### 5.2 API Testing
```bash
# Submit listing
curl -X POST http://localhost:8080/api/listings -H "Content-Type: application/json" -d '{"title":"Test","price":1000000,"address":"Test Address"}'

# Get pending
curl http://localhost:8080/api/admin/pending

# Approve
curl -X PUT http://localhost:8080/api/admin/listings/1/approve
```

---

## File Changes Summary

| Action | File |
|--------|------|
| MODIFY | `model/Property.java` - Add owner fields |
| NEW | `config/WebConfig.java` - Static resource handler |
| NEW | `controller/ListingController.java` - Seller endpoint |
| NEW | `controller/AdminListingController.java` - Admin endpoints |
| MODIFY | `service/PropertyService.java` - Add pending methods |
| MODIFY | `repository/PropertyRepository.java` - Add query |
| MODIFY | `frontend/sell-property.html` - API integration |
| MODIFY | `frontend/admin-dashboard.html` - Pending view |

---

## Timeline Estimate

| Phase | Effort |
|-------|--------|
| Phase 1 | 10 min |
| Phase 2 | 20 min |
| Phase 3 | 15 min |
| Phase 4 | 30 min |
| Phase 5 | 10 min |
| **Total** | ~85 min |
