#!/usr/bin/env python3
import json
import sys
import os

def convert_json_to_properties(input_file, output_file):
  with open(input_file, 'r', encoding='utf-8') as f:
    tank_data = json.load(f)

  if not isinstance(tank_data, dict) or 'data' not in tank_data:
    print("Error: Input JSON does not have the expected structure. 'data' key not found.")
    return False

  tanks = tank_data['data']

  os.makedirs(os.path.dirname(output_file), exist_ok=True)

  with open(output_file, 'w', encoding='utf-8') as f:
    for tank_id, tank_info in sorted(tanks.items(), key=lambda x: x[1].get('tag', '')):
      if all(key in tank_info for key in ['nation', 'tag', 'name']):
        nation = tank_info['nation']
        tag = tank_info['tag']
        name = tank_info['name']

        escaped_name = ""
        for char in name:
          if ord(char) < 0x0020 or ord(char) > 0x007E:
            escaped_name += "\\u{:04x}".format(ord(char))
          else:
            if char in [':', '=', '\\']:
              escaped_name += '\\' + char
            else:
              escaped_name += char

        property_line = "{}\\:{}={}\n".format(nation, tag, escaped_name)
        f.write(property_line)
      else:
        missing_keys = [k for k in ['nation', 'tag', 'name'] if k not in tank_info]
        print("Warning: Tank ID {} is missing required fields: {}".format(tank_id, ', '.join(missing_keys)))

  print("Conversion complete! Data written to {}".format(output_file))
  return True

def main():
  if len(sys.argv) < 3:
    print("Usage: python script.py input.json output.properties")
    return

  input_file = sys.argv[1]
  output_file = sys.argv[2]

  input_file = os.path.normpath(input_file)
  output_file = os.path.normpath(output_file)

  try:
    convert_json_to_properties(input_file, output_file)
  except FileNotFoundError:
    print("Error: Could not find the input file '{}'".format(input_file))
  except json.JSONDecodeError:
    print("Error: The file '{}' does not contain valid JSON data".format(input_file))
  except Exception as e:
    print("Error: An unexpected error occurred: {}".format(str(e)))

if __name__ == "__main__":
  main()