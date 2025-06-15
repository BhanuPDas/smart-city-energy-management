export class Email {
  private readonly value: string;

  constructor(private email: string) {
    this.value = email.trim().toLowerCase();
  }

  public static create(raw: string): Email {
    return new Email(raw);
  }

  public toString(): string {
    return this.value;
  }

  toJSON(): string {
    return this.value;
  }
}
