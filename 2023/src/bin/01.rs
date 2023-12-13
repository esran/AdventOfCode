use aoc2023::{args};
use log::{debug};
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
    let day = 1;
    match args.kind {
        args::Version::Example1 => check_example(day, 1, false, &extract_word_number_from_line),
        args::Version::Example2 => check_example(day, 2, true, &extract_word_number_from_line),
        args::Version::V1 => run_version(day, 1, false, &extract_word_number_from_line),
        args::Version::V2 => run_version(day, 2, true, &extract_word_number_from_line),
    }
}

fn extract_word_number_from_line(line: &str, allow_words: &bool) -> i32 {
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

        // check for possible spelled numbers (skipping zero) if allowed
        if *allow_words {
            debug!("  checking for number words");
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
    }

    if found_first && found_last {
        return first * 10 + last;
    }
    0
}
