use super::loader;

use loader::{load_lines};
use crate::loader::load_and_sum;

pub fn check_example<T>(day: i32, example: i32, context: T, func: &dyn Fn(&str, &T) -> i32) {
    let answer = format!("data/{:02}.example.{:}.answer", day, example);
    let answer = load_lines(&answer);
    let answer = answer[0].parse::<i32>().unwrap();

    let input = format!("data/{:02}.example.{:}", day, example);
    let total = load_and_sum(&input, context, func);

    assert_eq!(answer, total);

    println!("OK: day {}, example {}: {}", day, example, total);
}

pub fn run_version<T>(day: i32, version: i32, context: T, func: &dyn Fn(&str, &T) -> i32) {
    let input = format!("data/{:02}.input", day);
    let total = load_and_sum(&input, context, func);

    println!("Result: day {}, version {}: {}", day, version, total);
}
