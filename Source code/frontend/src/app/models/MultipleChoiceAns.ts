export interface MultipleChoiceAns {
    result?: boolean;
    quizReport?: QuizReport[];
    scores?: number;
    total?: number;
}

export interface QuizReport {
    question?: string;
    answer?: string;
    givenAnswer?: string;
    answerFlag?: number;
}