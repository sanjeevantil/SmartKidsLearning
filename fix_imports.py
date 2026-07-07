import os

def fix_imports_in_file(filepath):
    with open(filepath, 'r') as f:
        content = f.read()

    lines = content.split('\n')
    imports = set()
    last_import_idx = 0
    package_idx = 0
    for i, line in enumerate(lines):
        if line.startswith('package'):
            package_idx = i
        if line.startswith('import '):
            imports.add(line.strip())
            last_import_idx = max(last_import_idx, i)
    
    if last_import_idx == 0:
        last_import_idx = package_idx + 1

    added = []

    # Check for needed imports
    if 'CircleShape' in content and 'import androidx.compose.foundation.shape.CircleShape' not in imports:
        added.append('import androidx.compose.foundation.shape.CircleShape')
    if '.background(' in content and 'import androidx.compose.foundation.background' not in imports:
        added.append('import androidx.compose.foundation.background')
    if '.asStateFlow()' in content and 'import kotlinx.coroutines.flow.asStateFlow' not in imports:
        added.append('import kotlinx.coroutines.flow.asStateFlow')
    if 'ImageVector' in content and 'import androidx.compose.ui.graphics.vector.ImageVector' not in imports:
        added.append('import androidx.compose.ui.graphics.vector.ImageVector')
    if 'StiffnessMediumHigh' in content and 'import androidx.compose.animation.core.Spring' not in imports:
        added.append('import androidx.compose.animation.core.Spring')
    if 'Offset' in content and 'import androidx.compose.ui.geometry.Offset' not in imports:
        added.append('import androidx.compose.ui.geometry.Offset')
    if 'PointerInputChange' in content and 'import androidx.compose.ui.input.pointer.PointerInputChange' not in imports:
        added.append('import androidx.compose.ui.input.pointer.PointerInputChange')
    if 'brush = Brush.horizontalGradient' in content and 'import androidx.compose.ui.graphics.Brush' not in imports:
        added.append('import androidx.compose.ui.graphics.Brush')

    if added:
        lines = lines[:last_import_idx+1] + added + lines[last_import_idx+1:]
        with open(filepath, 'w') as f:
            f.write('\n'.join(lines))
        print(f"Fixed {filepath}")

for root, _, files in os.walk('app/src/main/java'):
    for file in files:
        if file.endswith('.kt'):
            fix_imports_in_file(os.path.join(root, file))
