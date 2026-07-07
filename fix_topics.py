import re

path = 'app/src/main/java/com/smartkids/learning/data/repository/TopicRepositoryImpl.kt'
with open(path, 'r') as f:
    lines = f.readlines()

new_lines = []
for line in lines:
    if 'TopicEntity(' in line and '0xFF' in line:
        # It's one of the topic lines
        # format is TopicEntity("id", "name", "res", "categoryId", "desc", "icon", color1, color2, ...)
        # We need to insert "categoryId" again as categoryName
        match = re.search(r'TopicEntity\("([^"]+)", "([^"]+)", "([^"]+)", "([^"]+)", "([^"]+)", "([^"]+)", (0x[0-9A-F]+), (0x[0-9A-F]+)(.*)\)', line)
        if match:
            # We want to change it to: TopicEntity("id", "name", "res", "category", "categoryName", "desc", "icon", ...)
            # Wait, let's assume categoryName is just the categoryId for now.
            g = match.groups()
            new_line = f'            TopicEntity("{g[0]}", "{g[1]}", "{g[2]}", "{g[3]}", "{g[3]}", "{g[4]}", "{g[5]}", {g[6]}, {g[7]}{g[8]})\n'
            # Let's verify what it has:
            # TopicEntity("abc_learning", "ABC Learning", "abc_learning", "Language", "Learn English alphabets A-Z", "alphabet", 0xFFFF6B35, 0xFFFFD166, 2, 6, 1, 26)
            # Match 3 is "Language". We insert it again.
            if len(new_line) > 10:
                line = new_line
        else:
            # fallback for simple replacement
            pass
            
    if 'existing.isNotEmpty()' in line:
        line = line.replace('existing.isNotEmpty()', 'existing.firstOrNull()?.isNotEmpty() == true')
        
    new_lines.append(line)

with open(path, 'w') as f:
    f.writelines(new_lines)
