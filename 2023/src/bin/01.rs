use aoc2023::args;
use log::{debug};
use simple_logger;


fn main() {
    let args = args::get_args();

    // print out the args
    println!("Args: {:?}", args);

    if args.debug {
        println!("Debug mode enabled");
        simple_logger::init_with_level(log::Level::Debug).unwrap();
    } else {
        simple_logger::init_with_level(log::Level::Warn).unwrap();
    }

    match args.kind {
        args::Version::Example1 => example1(),
        args::Version::Example2 => example2(),
        args::Version::V1 => version_1(),
        args::Version::V2 => version_2(),
    }
}

fn example1() {
    // process the example file
    let total = load_and_sum("data/01.example.1", &extract_number_from_line);

    // load the answer
    let answer = aoc2023::loader::load_lines("data/01.example.1.answer");
    // convert the answer to an integer
    let answer = answer[0].parse::<i32>().unwrap();

    // compare the answer to the total
    assert_eq!(answer, total);

    println!("Example: {}", total);
}

fn example2() {
    // process the example file
    let total = load_and_sum("data/01.example.2", &extract_word_number_from_line);

    // load the answer
    let answer = aoc2023::loader::load_lines("data/01.example.2.answer");
    // convert the answer to an integer
    let answer = answer[0].parse::<i32>().unwrap();

    // compare the answer to the total
    assert_eq!(answer, total);

    println!("Example: {}", total);
}

fn version_1() {
    // process the input file
    let total = load_and_sum("data/01.input", &extract_number_from_line);

    println!("V1: {}", total);
}

fn version_2() {
    // process the input file
    let total = load_and_sum("data/01.input", &extract_word_number_from_line);

    println!("V2: {}", total);
}

// Extract a number as the first and last digits from an
// alphanumeric string.
fn extract_number_from_line(line: &str) -> i32 {
    let mut first = 0;
    let mut last = 0;
    let mut found_first = false;
    let mut found_last = false;
    for c in line.chars() {
        if c.is_digit(10) {
            if !found_first {
                first = c.to_digit(10).unwrap() as i32;
                found_first = true;
            }

            last = c.to_digit(10).unwrap() as i32;
            found_last = true;
        }
    }
    if found_first && found_last {
        return first * 10 + last;
    }
    0
}

fn extract_word_number_from_line(line: &str) -> i32 {
    let number_words = vec!["zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"];

    let mut first = 0;
    let mut last = 0;
    let mut found_first = false;
    let mut found_last = false;

    debug!("line: {}", line);

    // iterate through the line tracking the current position
    for (p, c) in line.chars().enumerate() {
        if c.is_digit(10) {
            if !found_first {
                first = c.to_digit(10).unwrap() as i32;
                found_first = true;
                debug!("found first digit: {}", first)
            }

            last = c.to_digit(10).unwrap() as i32;
            found_last = true;
            debug!("  found last digit: {}", last)
        }

        // check for possible spelled numbers (skipping zero)
        for (i, n) in number_words.iter().enumerate() {
            // skip zero
            if i == 0 {
                continue;
            }

            // check whether the text at the current position matches the word
            if line[p..].starts_with(n) {
                if !found_first {
                    first = i as i32;
                    found_first = true;

                    debug!("  found first word: {}", first)
                }

                last = i as i32;
                found_last = true;
                debug!("  found last word: {}", last)
            }
        }
    }

    if found_first && found_last {
        return first * 10 + last;
    }
    0
}

// Load a file and extract the first and last digits from each line
// and sum them. Extract using an optional function.
fn load_and_sum(filename: &str, extract_func: &dyn Fn(&str) -> i32) -> i32 {
    let lines = aoc2023::loader::load_lines(filename);
    let mut total = 0;
    for line in lines {
        let num = extract_func(&line);
        total += num;
    }
    total
}
