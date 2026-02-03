# Agents Section Documentation

## Overview
The Agents section displays agents dynamically from the backend database.

## API Endpoints
| Endpoint | Description |
|----------|-------------|
| `GET /api/agents/public` | Get all ACTIVE agents (public) |
| `GET /api/agents/:id` | Get single agent by ID |
| `POST /api/agents` | Create agent (admin) |
| `PUT /api/agents/:id` | Update agent (admin) |
| `DELETE /api/agents/:id` | Delete agent (admin) |

## Frontend Files
- `frontend/agents.html` - Agent listing (fetches from API)
- `frontend/agent-profile.html` - Agent detail page (fetches from API)
- `frontend/public/agents/` - Static image fallbacks

## Agent Model Fields
- `id`, `name`, `email`, `phone`, `profileImageUrl`
- `title`, `bio`, `qualifications`, `degree`
- `experience`, `specialization`, `propertiesSold`, `rating`
- `status` (ACTIVE/INACTIVE)

## Admin Workflow
1. Admin adds agent via Admin Dashboard
2. Agent is saved to database with status ACTIVE
3. Frontend fetches from `/api/agents/public`
4. New agent appears automatically
