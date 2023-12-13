use aoc2023::args;
use simple_logger;
use aoc2023::runner::{check_example, run_version};

fn main() {
    let args = args::get_args();

    // Setup simple logger
    if args.debug {
        println!("Debug mode enabled");
        simple_logger::init_with_level(log::Level::Debug).unwrap();
    } else {
        simple_logger::init_with_level(log::Level::Warn).unwrap();
    }

    // Run the appropriate version
    let day = 2;
    let balls: Colours = Colours {
        red: 12,
        green: 13,
        blue: 14,
    };
    match args.kind {
        args::Version::Example1 => check_example(day, 1, balls, &part_one),
        args::Version::Example2 => check_example(day, 2, balls, &part_two),
        args::Version::V1 => run_version(day, 1, balls, &part_one),
        args::Version::V2 => run_version(day, 2, balls, &part_two),
    }
}


fn part_one(line: &str, balls: &Colours) -> i32 {
    let game = get_game_from_line(line);

    // If any play isn't possible then return 0
    for colours in game.colours {
        if ! check_possible(colours, balls) {
            return 0;
        }
    }

    // Otherwise return the game number
    game.number
}

fn part_two(line: &str, _balls: &Colours) -> i32 {
    let game = get_game_from_line(line);

    // Get the maximum number of balls in any play
    let max_balls = get_max_balls(game.colours);

    // Return the 'power' of these balls
    max_balls.red * max_balls.green * max_balls.blue
}

fn get_max_balls(colours: Vec<Colours>) -> Colours {
    let mut result: Colours = Colours {
        red: 0,
        green: 0,
        blue: 0,
    };

    for colour in colours {
        if colour.red > result.red {
            result.red = colour.red;
        }
        if colour.green > result.green {
            result.green = colour.green;
        }
        if colour.blue > result.blue {
            result.blue = colour.blue;
        }
    }

    result
}

fn check_possible(colours: Colours, contents: &Colours) -> bool {
    if colours.red > contents.red {
        return false;
    }
    if colours.green > contents.green {
        return false;
    }
    if colours.blue > contents.blue {
        return false;
    }
    true
}

fn get_game_from_line(line: &str) -> Game {
    // split the line on :
    let parts: Vec<&str> = line.split(":").collect();
    assert_eq!(parts.len(), 2);

    // split the first part on space
    let game_parts: Vec<&str> = parts[0].split(" ").collect();
    assert_eq!(game_parts.len(), 2);

    // Get the game number from the second part
    let mut result: Game = Game { number: 0, colours: Vec::new() };
    result.number = game_parts[1].parse::<i32>().unwrap();

    // split the second part on ;
    let play_parts: Vec<&str> = parts[1].split(";").collect();

    // iterate over the parts
    for play in play_parts {
        // split the part on ,
        let colour_parts: Vec<&str> = play.split(",").collect();

        for colour in colour_parts {
            // split the colour on space
            let ball_parts: Vec<&str> = colour.trim().split(" ").collect();
            assert_eq!(ball_parts.len(), 2);

            // Get the colour number from the second part
            let count = ball_parts[0].parse::<i32>().unwrap();
            let mut this_colour: Colours = Colours {
                red: 0,
                green: 0,
                blue: 0,
            };

            match ball_parts[1] {
                "red" => this_colour.red = count,
                "green" => this_colour.green = count,
                "blue" => this_colour.blue = count,
                _ => panic!("Unknown colour: {}", ball_parts[1]),
            }

            result.colours.push(this_colour);
        }
    }

    result
}

struct Colours {
    red: i32,
    green: i32,
    blue: i32,
}

struct Game {
    number: i32,
    colours: Vec<Colours>,
}
