# Refine Agents Page Directive

## Goal
Update the `agents.html` page to include a search/filter bar, ensure a 10-card grid layout, and proper contact button functionality. Also ensure agent images are present.

## Inputs
- `agents.html` location: `frontend/agents.html`
- Image Assets: `frontend/public/agents/`
- Target Design: 10 cards, "View" button, "Call" (WhatsApp), "Email" (Gmail).

## Execution Steps
1.  **Check/Create Assets**: Ensure `frontend/public/agents/` exists and contains `agent1.png` to `agent4.png`.
2.  **Generate HTML**: Create the HTML content for `agents.html` including:
    - Search input (Filter by name/role).
    - Grid container.
    - 10 static agent cards (cycling through the 4 images).
    - "all" filter logic (simple JS).
3.  **Write File**: Save the HTML to `frontend/agents.html`.

## Output
- Updated `agents.html`.
- Populated `public/agents/` directory.
