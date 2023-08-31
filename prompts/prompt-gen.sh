#!/bin/bash

echo "Please input your requirements file path:"
read -r file_path

if [ -f "$file_path" ]; then
    content=$(cat "$file_path")
else
    echo "File not found: $file_path"
fi

content="$content\n"
echo "$content"

# echo "Please input your prompt template path:"
# read -r template  
template=./prompt-template-crud-v1.md
tempfile=./generated-tmp.md
cp "$template" "$tempfile"

requirements_line=$(grep -n "# Requirements:" "$tempfile" | cut -d ':' -f 1)
goals_line=$(grep -n "# Goals:" "$tempfile" | cut -d ':' -f 1)

echo "requirements line: $requirements_line"
echo "goals line: $goals_line"

if [[ -n "$requirements_line" && -n "$goals_line" ]]; then
    requirements_line=$((requirements_line + 1))
    goals_line=$((goals_line - 1))

    sed -i "${requirements_line},${goals_line}d" "$tempfile"
    echo "$tempfile"
    content=$(echo "$content" | sed ':a;N;$!ba;s/\n/\\n/g')
    echo "$content"
    sed -i "${requirements_line}i $content" "$tempfile"

    outname="generated-$(date +%Y%m%d-%H%M%S).md"
    cat "$tempfile" > "$outname"

    echo "File '$outname' generated successfully."
else
    echo "Error: Could not find '# Requirements:' and/or '# Goals:' in the template."
fi

rm "$tempfile"
