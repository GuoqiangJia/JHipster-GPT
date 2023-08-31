#!/bin/bash

echo "Please input your requirements:"
read -r file_path

if [ -f "$file_path" ]; then
    content=$(cat "$file_path")
else
    echo "File not found: $file_path"
fi

content="$content"\r\n
echo "$content"

echo "Please input your prompt template path:"
read -r template  

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
    sed -i "${requirements_line}i $content" "$tempfile"

    outname="generated-$(date +%Y%m%d-%H%M%S).md"
    cat "$tempfile" > "$outname"

    echo "File '$outname' generated successfully."
else
    echo "Error: Could not find '# Requirements:' and/or '# Goals:' in the template."
fi

rm "$tempfile"
