use std::cmp::{min};
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
    let day = 3;
    let mut context = Context {
        prev_line: LineSegments {
            line: "".to_string(),
            segments: Vec::new(),
        },
        this_line: LineSegments {
            line: "".to_string(),
            segments: Vec::new(),
        },
    };
    match args.kind {
        args::Version::Example1 => check_example(day, 1, &mut context, &part_one),
        args::Version::Example2 => check_example(day, 2, &mut context, &part_two),
        args::Version::V1 => run_version(day, 1, &mut context, &part_one),
        args::Version::V2 => run_version(day, 2, &mut context, &part_two),
    }
}

struct Context {
    prev_line: LineSegments,
    this_line: LineSegments,
}

#[derive(Clone)]
struct LineSegments {
    line: String,
    segments: Vec<Segment>,
}

impl LineSegments {
    fn check_against_other_line(&mut self, line: &str) -> i32 {
        if line.len() == 0 {
            return 0;
        }

        let mut result = 0;

        for segment in &mut self.segments {
            if segment.processed {
                continue;
            }

            let mut start = segment.start;
            if start > 0 { start -= 1; }
            let end = min(segment.end + 1, line.len());

            for c in line[start..end].chars() {
                if c.is_ascii_digit() || c == '.' {
                    continue;
                }

                segment.processed = true;

                let number = &self.line[segment.start..segment.end];
                let number = number.parse::<i32>().unwrap();

                result += number;
            }
        }

        result
    }

    fn check_self(&mut self) -> i32 {
        let mut result = 0;

        for segment in &mut self.segments {
            if segment.processed {
                continue;
            }

            let number = &self.line[segment.start..segment.end];
            let number = number.parse::<i32>().unwrap();

            if segment.start > 0 && self.line.as_bytes()[segment.start - 1] as char != '.' {
                result += number;
                segment.processed = true;
                continue;
            }

            if segment.end < self.line.len() && self.line.as_bytes()[segment.end] as char != '.' {
                result += number;
                segment.processed = true;
            }
        }

        result
    }
}

#[derive(Clone)]
struct Segment {
    start: usize,
    end: usize,
    processed: bool,
}

fn part_one(line: &str, context: &mut Context) -> i32 {
    context.prev_line = context.this_line.clone();
    context.this_line = make_line_segments(line);

    let mut result = 0;

    result += context.this_line.check_self();

    result += context.prev_line.check_against_other_line(line);
    result += context.this_line.check_against_other_line(&context.prev_line.line);

    result
}

fn part_two(_line: &str, _context: &mut Context) -> i32 {
    0
}

fn make_line_segments(line: &str) -> LineSegments {
    let mut result = LineSegments {
        line: line.to_string(),
        segments: Vec::new(),
    };

    let mut in_number = false;
    let mut segment: Segment = Segment {
        start: 0,
        end: 0,
        processed: false,
    };
    for (i, c) in line.chars().enumerate() {
        if c.is_ascii_digit() && !in_number {
            segment = Segment {
                start: i,
                end: i,
                processed: false,
            };
            in_number = true;
        } else if in_number && !c.is_ascii_digit() {
            segment.end = i;
            result.segments.push(segment.clone());
            in_number = false;
        }
    }

    if in_number {
        segment.end = line.len();
        result.segments.push(segment.clone());
    }

    result
}