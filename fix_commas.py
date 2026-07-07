path = 'app/src/main/java/com/smartkids/learning/data/repository/TopicRepositoryImpl.kt'
with open(path, 'r') as f:
    lines = f.readlines()

for i, line in enumerate(lines):
    if 'TopicEntity(' in line and '0xFF' in line:
        if not line.strip().endswith(','):
            # Check if this is the last one
            if i + 1 < len(lines) and 'TopicEntity' in lines[i+1]:
                lines[i] = line.rstrip() + ',\n'

with open(path, 'w') as f:
    f.writelines(lines)
