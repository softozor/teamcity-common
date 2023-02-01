import sys
from argparse import Namespace, ArgumentParser

from jelastic_client import JelasticClientFactory


def parse_cmd_line_args() -> Namespace:
    parser = ArgumentParser()
    parser.add_argument("--jelastic-api-url", required=True, type=str, action="store")
    parser.add_argument(
        "--jelastic-access-token", required=True, type=str, action="store"
    )
    parser.add_argument("--env-name", required=True, type=str, action="store")
    args = parser.parse_args()
    return args


def main():
    args = parse_cmd_line_args()
    client_factory = JelasticClientFactory(
        args.jelastic_api_url, args.jelastic_access_token
    )
    control_client = client_factory.create_control_client()
    env_info = control_client.get_env_info(args.env_name)
    if not env_info.exists():
        print(f"Environment {args.env_name} does not exists.")
        return

    control_client.delete_env(args.env_name)

    env_info = control_client.get_env_info(args.env_name)
    print("env info: ", env_info.status())
    if env_info.exists():
        sys.exit(1)


if __name__ == "__main__":
    main()
