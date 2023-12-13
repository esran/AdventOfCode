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
