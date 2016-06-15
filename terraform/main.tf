
provider "aws" {
    region = "sa-east-1"
}

resource "aws_instance" "web" {
    ami = "ami-106ee57c"
    instance_type = "t2.micro"
    key_name = "${aws_key_pair.key.key_name}"
    tags {
        Name = "CoffeeBreak"
    }
    user_data = "${file("startup.sh")}"
    security_groups = ["${aws_security_group.allow_80_22.name}"]
}

resource "aws_key_pair" "key" {
    key_name = "coffee_key"
    public_key = "ssh-rsa ...."
}

resource "aws_security_group" "allow_80_22" {
    name = "Allow_80_22"
    description = "Allow all inbound traffic"

    ingress {
        from_port = 80
        to_port = 80
        protocol = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }

    ingress {
        from_port = 22
        to_port = 22
        protocol = "tcp"
        cidr_blocks = ["0.0.0.0/0"]
    }
}

output "lb" {
    value = "${aws_instance.web.public_ip}"
}