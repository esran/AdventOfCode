use std::fs::File;
use std::io::{BufRead, BufReader};

pub fn load_lines(filename: &str) -> Vec<String> {
    let mut lines = Vec::new();
    let file = File::open(filename).expect("file not found");
    let buf = BufReader::new(file);
    for line in buf.lines() {
        lines.push(line.unwrap());
    }
    lines
}

pub fn load_and_sum<T>(filename: &str, context: T, process_func: &dyn Fn(&str, &T) -> i32) -> i32 {
    let lines = load_lines(filename);
    let mut total = 0;
    for line in lines {
        total += process_func(&line, &context);
    }
    total
}
