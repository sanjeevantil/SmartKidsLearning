import os

for root, _, files in os.walk('app/src/main/java'):
    for file in files:
        if file.endswith('.kt'):
            filepath = os.path.join(root, file)
            with open(filepath, 'r') as f:
                content = f.read()
            if 'CircleShape()' in content:
                content = content.replace('CircleShape()', 'CircleShape')
                with open(filepath, 'w') as f:
                    f.write(content)
                print(f"Fixed CircleShape() in {filepath}")
