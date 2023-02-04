import argparse
import re

from jelastic_client import get_manifest_data


def pascal_to_all_caps(input: str) -> str:
    words = [word.upper() for word in re.findall(r"[A-Z][a-z]*", input)]
    return "_".join(words)


def main(success_text_file: str):
    with open(success_text_file, "r") as file:
        success_text_content = file.read()
        variables = get_manifest_data(success_text_content)
        for key, value in variables.items():
            print(
                f"##teamcity[setParameter name='env.{pascal_to_all_caps(key)}' value='{value}']"
            )


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--success-text-file",
        action="store",
        required=True,
    )
    args = parser.parse_args()
    main(args.success_text_file)
