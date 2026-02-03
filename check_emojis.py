import requests

base_url = "https://raw.githubusercontent.com/Tarikul-Islam-Anik/Telegram-Animated-Emojis/main"

# Search logic: check common categories for a name
categories = [
    "Smileys", "People", "Animals and Nature", "Food and Drink", 
    "Travel and Places", "Activities", "Objects", "Symbols"
]

names_to_check = [
    "Smiling Face with Hearts", "Smiling Face With Hearts", "Smiling Face with Heart-Eyes",
    "Money Bag", "Money-Mouth Face",
    "Chart Increasing", "Chart Increasing with Yen",
    "Globe Showing Asia-Australia", "Globe Showing Europe-Africa", "Globe Showing Americas",
    "Coin", "Gem Stone",
    "Camera", "Camera With Flash",
    "Person Swimming", "Man Swimming", "Woman Swimming",
    "Dumbbell", "Flexed Biceps",
    "Locked", "Locked with Key", "Lock", "Key",
    "Shield", "Shield with Check Mark",
    "Couch and Lamp", "Sofa", "Bed", "Chair",
    "House with Garden", "House", "Building Construction",
    "Sport Utility Vehicle", "Police Car", "Oncoming Automobile",
    "Beverage Box", "Cocktail Glass"
]

print(f"Checking {len(names_to_check)} names across {len(categories)} categories...")

found = {}

for name in names_to_check:
    for category in categories:
        # Check standard casing
        url = f"{base_url}/{category}/{name}.webp"
        try:
            r = requests.head(url, timeout=1)
            if r.status_code == 200:
                print(f"FOUND: {name} -> {category}/{name}.webp")
                found[name] = f"{category}/{name}.webp"
                break # Found, move to next name
        except:
            pass
        
        # Check Title Case if different
        if name != name.title():
            title_name = name.title()
            url = f"{base_url}/{category}/{title_name}.webp"
            try:
                r = requests.head(url, timeout=1)
                if r.status_code == 200:
                    print(f"FOUND: {title_name} -> {category}/{title_name}.webp")
                    found[name] = f"{category}/{title_name}.webp"
                    break
            except:
                pass

print("--- SUMMARY ---")
for k, v in found.items():
    print(f"{k}: {v}")
