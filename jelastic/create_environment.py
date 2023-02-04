import json
import sys
import urllib.request
from argparse import Namespace, ArgumentParser

import yaml
from jelastic_client import JelasticClientFactory


def parse_cmd_line_args() -> Namespace:
    parser = ArgumentParser()
    parser.add_argument("--jelastic-api-url", required=True, type=str, action="store")
    parser.add_argument(
        "--jelastic-access-token", required=True, type=str, action="store"
    )
    parser.add_argument("--env-name", required=True, type=str, action="store")
    parser.add_argument("--manifest-url", required=True, type=str, action="store")
    parser.add_argument("--success-text-query", default=None, type=str, action="store")
    parser.add_argument("--json-settings-file", type=str, action="store")
    parser.add_argument("--region", type=str, action="store")
    parser.add_argument(
        "--output-success-text-file", required=True, type=str, action="store"
    )
    args = parser.parse_args()
    return args


def read_settings_from_file(filename: str) -> dict:
    with open(filename, "r") as file:
        settings = json.load(file)
    return settings


def is_update_manifest(manifest_url: str) -> bool:
    with urllib.request.urlopen(manifest_url) as response:
        manifest_content = response.read().decode()
        manifest_data = yaml.safe_load(manifest_content)
    return manifest_data["type"] == "update"


def main():
    args = parse_cmd_line_args()
    client_factory = JelasticClientFactory(
        args.jelastic_api_url, args.jelastic_access_token
    )
    control_client = client_factory.create_control_client()
    env_info = control_client.get_env_info(args.env_name)

    is_update = is_update_manifest(args.manifest_url)
    is_install = not is_update

    if (is_update and not env_info.is_running()) or (
        is_install and env_info.is_running()
    ):
        print(
            f"Cannot {'update' if is_update else 'install'} environment {args.env_name}."
        )
        sys.exit(1)

    jps_client = client_factory.create_jps_client()
    settings = (
        read_settings_from_file(args.json_settings_file)
        if args.json_settings_file
        else None
    )
    success = (
        {
            "email": False,
            "text": args.success_text_query,
        }
        if args.success_text_query
        else None
    )
    success_text = jps_client.install_from_url(
        url=args.manifest_url,
        env_name=args.env_name,
        settings=settings,
        region=args.region,
        success=success,
    )

    with open(args.output_success_text_file, "w") as file:
        file.write(success_text)

    env_info = control_client.get_env_info(args.env_name)
    print("env info: ", env_info.status())
    if not env_info.is_running():
        sys.exit(1)


if __name__ == "__main__":
    main()
