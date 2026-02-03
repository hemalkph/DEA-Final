
import os
import shutil

# Configuration
FRONTEND_DIR = os.path.join(os.getcwd(), 'frontend')
PUBLIC_AGENTS_DIR = os.path.join(FRONTEND_DIR, 'public', 'agents')
AGENTS_HTML = os.path.join(FRONTEND_DIR, 'agents.html')

# Ensure agents directory exists
if not os.path.exists(PUBLIC_AGENTS_DIR):
    os.makedirs(PUBLIC_AGENTS_DIR)
    print(f"Created directory: {PUBLIC_AGENTS_DIR}")

# Images check
expected_images = ['agent1.png', 'agent2.png', 'agent3.png', 'agent4.png']
missing = [img for img in expected_images if not os.path.exists(os.path.join(PUBLIC_AGENTS_DIR, img))]
if missing:
    print(f"CRITICAL: Missing images: {missing}. HTML will be updated, but images won't show!")
else:
    print("All agent images found.")

# Unique Data Generation
# We'll create distinct data for 10 agents
agents_data = []
roles = ['Senior Real Estate Agent', 'Luxury Property Specialist', 'Residential Sales Manager', 'Commercial Real Estate expert', 'Investment Consultant', 'Senior Broker', 'Property Analyst', 'Real Estate Consultant', 'Sales Executive', 'Head of Sales']

for i in range(1, 11):
    img_idx = (i - 1) % 4
    img_name = f'agent{img_idx + 1}.png'
    
    agent_name = f"Agent {i}"
    # Assign specific names to make it look professional
    names = ["John Smith", "Sarah Johnson", "Michael Williams", "Emily Brown", "David Jones", "Jessica Garcia", "Robert Miller", "Jennifer Davis", "William Rodriguez", "Elizabeth Martinez"]
    if i <= len(names):
        agent_name = names[i-1]

    # Unique contacts
    phone = f"12345678{i:02d}" # 1234567801, 1234567802...
    email = f"{agent_name.lower().replace(' ', '.')}.{i}@gmail.com"
    role = roles[i-1]
    
    agents_data.append({
        'id': i,
        'name': agent_name,
        'role': role,
        'image': f'/agents/{img_name}',
        'phone': phone,
        'email': email
    })

# Construct JSON string for JS
import json
json_data = json.dumps(agents_data, indent=4)

# HTML Content
html_content = f"""<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Our Agents - Real Estate</title>
</head>

<body class="bg-gray-50 font-sans antialiased text-gray-900">
    <div id="navbar"></div>

    <div class="max-w-7xl mx-auto py-12 px-4 sm:px-6 lg:px-8">
        <!-- Header Section -->
        <div class="text-center mb-12">
            <span class="inline-block px-4 py-2 bg-green-100 text-green-700 rounded-full text-sm font-semibold mb-4">
                Meet Our Team
            </span>
            <h1 class="text-4xl md:text-5xl font-extrabold text-gray-900 tracking-tight mb-4">
                Expert <span class="text-green-600">Real Estate Agents</span>
            </h1>
            <p class="text-lg text-gray-600 max-w-2xl mx-auto mb-8">
                Our team of certified professionals is dedicated to helping you find your perfect property.
            </p>
            
            <!-- Search Filter -->
            <div class="max-w-md mx-auto relative group">
                <input type="text" id="agentSearch" placeholder="Search agents by name or role..." 
                       class="w-full pl-12 pr-4 py-3 rounded-full border border-gray-200 focus:border-green-500 focus:ring-2 focus:ring-green-200 transition-all outline-none shadow-sm group-hover:shadow-md">
                <svg class="w-5 h-5 text-gray-400 absolute left-4 top-1/2 transform -translate-y-1/2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                </svg>
            </div>
        </div>

        <!-- Agents Grid -->
        <div id="agentsGrid" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            <!-- Agents will be rendered here by JS -->
        </div>
        
        <!-- No Results Message -->
        <div id="noResults" class="hidden text-center py-12">
            <div class="inline-flex items-center justify-center w-16 h-16 rounded-full bg-gray-100 mb-4">
                <svg class="w-8 h-8 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                </svg>
            </div>
            <p class="text-gray-500 text-lg">No agents found matching your search.</p>
            <button onclick="document.getElementById('agentSearch').value = ''; document.getElementById('agentSearch').dispatchEvent(new Event('input'))" 
                    class="mt-2 text-green-600 font-semibold hover:text-green-700">Clear Search</button>
        </div>
    </div>

    <!-- CTA Section -->
    <div class="bg-gradient-to-r from-green-600 to-green-900 py-16 mt-16">
        <div class="max-w-4xl mx-auto text-center px-4">
            <h2 class="text-3xl font-bold text-white mb-4">Ready to Find Your Dream Home?</h2>
            <p class="text-green-100 mb-8">Connect with one of our expert agents today.</p>
            <a href="/contact.html"
                class="inline-flex items-center px-8 py-4 bg-white text-green-600 font-bold rounded-xl shadow-xl hover:shadow-2xl transition-all duration-300 hover:-translate-y-1">
                Contact Us Today
            </a>
        </div>
    </div>

    <script type="module" src="/src/main.js"></script>
    <script type="module">
        const grid = document.getElementById('agentsGrid');
        const searchInput = document.getElementById('agentSearch');
        const noResults = document.getElementById('noResults');

        // Data generated from Python
        const agentsData = {json_data};

        const renderAgents = (agents) => {{
            if (agents.length === 0) {{
                grid.innerHTML = '';
                noResults.classList.remove('hidden');
                return;
            }}
            
            noResults.classList.add('hidden');
            grid.innerHTML = agents.map(agent => `
                <div class="group bg-white rounded-2xl shadow-sm hover:shadow-xl transition-all duration-300 overflow-hidden border border-gray-100 flex flex-col h-full ring-1 ring-gray-50 ring-offset-0">
                    <!-- Image -->
                    <div class="relative h-72 overflow-hidden bg-gray-50">
                        <img src="${{agent.image}}" 
                             alt="${{agent.name}}"
                             class="w-full h-full object-cover object-top transform group-hover:scale-105 transition-transform duration-500"
                             loading="lazy"
                             onerror="this.src='https://via.placeholder.com/400x500?text=Agent+Image'">
                        
                        <!-- View Profile Button Overlay -->
                        <div class="absolute inset-0 bg-black/20 opacity-0 group-hover:opacity-100 transition-opacity duration-300 flex items-center justify-center backdrop-blur-sm">
                            <button onclick="alert('Viewing profile for ${{agent.name}}')" 
                                    class="bg-white text-green-700 font-bold py-2.5 px-6 rounded-full transform translate-y-4 group-hover:translate-y-0 transition-all duration-300 shadow-lg hover:scale-105 active:scale-95 flex items-center gap-2">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/></svg>
                                View Profile
                            </button>
                        </div>
                    </div>

                    <!-- Content -->
                    <div class="p-6 flex-1 flex flex-col">
                        <div class="mb-4">
                            <h3 class="text-xl font-bold text-gray-900 mb-1 group-hover:text-green-700 transition-colors">${{agent.name}}</h3>
                            <p class="text-green-600 font-medium text-sm">${{agent.role}}</p>
                        </div>
                        
                        <div class="mt-auto grid grid-cols-2 gap-3 pt-4 border-t border-gray-50">
                            <!-- WhatsApp Button -->
                            <a href="https://wa.me/${{agent.phone}}" target="_blank" rel="noopener noreferrer"
                               class="flex items-center justify-center gap-2 py-2.5 px-3 bg-green-50 text-green-700 hover:bg-green-600 hover:text-white rounded-xl font-semibold transition-all duration-300 group/btn shadow-sm hover:shadow-md">
                                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24"><path d="M.057 24l1.687-6.163c-1.041-1.804-1.588-3.849-1.587-5.946.003-6.556 5.338-11.891 11.893-11.891 3.181.001 6.167 1.24 8.413 3.488 2.245 2.248 3.481 5.236 3.48 8.414-.003 6.557-5.338 11.892-11.893 11.892-1.99-.001-3.951-.5-5.688-1.448l-6.305 1.654zm6.597-3.807c1.676.995 3.276 1.591 5.392 1.592 5.448 0 9.886-4.434 9.889-9.885.002-5.462-4.415-9.89-9.881-9.892-5.452 0-9.887 4.434-9.889 9.884-.001 2.225.651 3.891 1.746 5.634l-.999 3.648 3.742-.981zm11.387-5.464c-.074-.124-.272-.198-.57-.347-.297-.149-1.758-8.683-2.03-9.672-.272-.989-.47-1.138-.644-1.138-.174 0-.371-.002-.569-.002s-.644.074-.991.446c-.347.373-1.336 1.214-1.336 2.964s1.366 3.435 1.514 3.633c.149.199 2.645 4.195 6.463 5.797 2.671 1.121 3.235.9 3.83.823.594-.076 1.905-.792 2.177-1.56.272-.768.272-1.413.198-1.537z"/></svg>
                                Call
                            </a>
                            <!-- Gmail Button -->
                            <a href="https://mail.google.com/mail/?view=cm&fs=1&to=${{agent.email}}" target="_blank" rel="noopener noreferrer"
                               class="flex items-center justify-center gap-2 py-2.5 px-3 bg-gray-50 text-gray-700 hover:bg-red-500 hover:text-white rounded-xl font-semibold transition-all duration-300 group/btn shadow-sm hover:shadow-md">
                                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z"></path></svg>
                                Email
                            </a>
                        </div>
                    </div>
                </div>
            `).join('');
        }};

        // Filter Logic
        searchInput.addEventListener('input', (e) => {{
            const searchTerm = e.target.value.toLowerCase();
            const filteredAgents = agentsData.filter(agent => 
                agent.name.toLowerCase().includes(searchTerm) || 
                agent.role.toLowerCase().includes(searchTerm)
            );
            renderAgents(filteredAgents);
        }});

        renderAgents(agentsData);
    </script>
</body>

</html>
"""

with open(AGENTS_HTML, 'w', encoding='utf-8') as f:
    f.write(html_content)

print(f"Successfully updated {AGENTS_HTML} with {len(agents_data)} agents.")
