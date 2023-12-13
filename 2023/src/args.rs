// Contents: Command line arguments
use argh::FromArgs;

#[derive(Debug)]
pub enum Version {
    Example1,
    Example2,
    V1,
    V2,
}

#[derive(FromArgs, Debug)]
#[argh(description = "Advent of Code Arguments")]
pub struct Args {
    /// choose version to run
    #[argh(option, short = 'v', long = "version", from_str_fn(parse_version))]
    pub kind: Version,

    /// debug mode
    #[argh(switch, short = 'd', long = "debug")]
    pub debug: bool,
}

fn parse_version(s: &str) -> Result<Version, String> {
    match s {
        "e1" => Ok(Version::Example1),
        "e2" => Ok(Version::Example2),
        "1" => Ok(Version::V1),
        "2" => Ok(Version::V2),
        _ => Err(format!("unknown version: {}", s)),
    }
}

pub fn get_args() -> Args {
    argh::from_env()
}
